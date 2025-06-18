package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.MemberRepository
import com.kim.ktboot.service.MemberService
import com.kim.ktboot.utils.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/login") // API 요청을 위한 기본 경로
class LoginController (
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
){

    @PostMapping("/cert")
    fun loginCert(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val member = memberRepository.findByMemId(request.id)
                ?: return ResponseEntity.status(401).body(mapOf("error" to "아이디가 존재하지 않습니다."))

        if (!passwordEncoder.matches(request.pass, member.memPassword)) {
            return ResponseEntity.status(401).body(mapOf("error" to "비밀번호가 일치하지 않습니다."))
        }

        val token = jwtTokenProvider.createAccessToken(request.id)

        // 토큰 DB에 저장 (선택적)
        memberRepository.save(member.copy(id = member.id, memAccessToken = token))

        val userInfo = MemberList(
                memberIdx = member.id,
                memberId = member.memId,
                memberName = member.memName,
                memberGender = member.memGender,
                memberCreatedAt = member.memCreatedAt
        )

        val headers = org.springframework.http.HttpHeaders()
        headers["Authorization"] = "Bearer $token"

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(userInfo)
    }



}
