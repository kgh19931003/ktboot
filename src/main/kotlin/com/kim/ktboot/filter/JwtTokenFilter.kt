package com.kim.ktboot.filter


import com.nimbusds.oauth2.sdk.http.HTTPResponse
import com.kim.ktboot.proto.isNotNull
import com.kim.ktboot.service.MemberService
import com.kim.ktboot.utils.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter (

) : OncePerRequestFilter() {

    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            jwtTokenProvider.resolveToken(request).let{
                if(it.isNotBlank()) jwtVerify(request, response, filterChain)
            }
        } catch (e: ExpiredJwtException) {
            logger.error("ExpiredJwtException: ${e.message}")
        } catch (e: Exception) {
            logger.error("token filter error: ${e.message}")
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val pathsToExclude = listOf(
            "/test",
            "/favicon.ico",
            "/actuator",
            "/error",
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/v1/api-docs",
            "/v3/api-docs/swagger-config",
            "/swagger-ui/index.html",
            "/api/**",
        )
        val path = request.requestURI
        return pathsToExclude.any { path.startsWith("/pg$it") }
    }


    private fun jwtVerify(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = jwtTokenProvider.resolveToken(request)
            val isAccessTokenValid = jwtTokenProvider.validateAccessToken(token)
            var isRefreshTokenValid = false

            if(!isAccessTokenValid) isRefreshTokenValid = jwtTokenProvider.validateRefreshToken(token)

            when {
                isAccessTokenValid -> {
                    // 액세스 토큰이 유효한 경우 일반적인 인증 진행
                    val loginId = verifyJwtMemId(request, response)
                    setAuthenticationContext(loginId)
                }
                isRefreshTokenValid -> {
                    // 액세스 토큰은 만료되었지만 리프레시 토큰이 유효한 경우
                    val loginId = jwtTokenProvider.getRefreshUserPk(token)

                    // DB에 저장된 리프레시 토큰과 비교 검증
                    val storedRefreshToken = memberService.getStoredRefreshToken(loginId)
                    if (token == storedRefreshToken) {

                        // 새로운 액세스 및 리프레시 토큰 생성
                        val newAccessToken = jwtTokenProvider.createAccessToken(loginId)
                        val newRefreshToken = jwtTokenProvider.createRefreshToken(loginId)

                        // 토큰 저장
                        memberService.save(memberService.findByMemId(loginId).copy(memAccessToken = newAccessToken, memRefreshToken = newRefreshToken))

                        // 응답 헤더에 새로운 액세스 토큰 추가
                        response.addHeader("acessToken", newAccessToken)
                        response.addHeader("refreshToken", newRefreshToken)

                        // 인증 컨텍스트 설정
                        setAuthenticationContext(loginId)
                    } else {
                        throw ExpiredJwtException(null, null, "유효하지 않은 리프레시 토큰입니다")
                    }
                }
                else -> {
                    memberService.deleteAccessToken(jwtTokenProvider.getAccessUserPk(token))
                    memberService.deleteRefreshToken(jwtTokenProvider.getRefreshUserPk(token))
                    throw ExpiredJwtException(null, null, "모든 토큰이 만료되었습니다")
                }
            }

        }catch (e: ExpiredJwtException) {
            throw e
        }
    }


    private fun verifyJwtMemId(request: HttpServletRequest, response: HttpServletResponse): String {
        jwtTokenProvider.resolveToken(request).apply{
            if (isBlank()) {
                return HTTPResponse.SC_UNAUTHORIZED.toString()
            }
            else{
                return jwtTokenProvider.getAccessUserPk(this)
            }
        }
    }


    // 인증 컨텍스트 설정을 위한 헬퍼 함수
    private fun setAuthenticationContext(loginId: String) {
        MDC.put("memberId", loginId)
        val member = memberService.loadUserByMemberId(loginId)
        val authentication = UsernamePasswordAuthenticationToken(member, member.password, member.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }


}