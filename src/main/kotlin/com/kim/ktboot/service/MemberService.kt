package com.kim.ktboot.service



import com.kim.ktboot.form.ListPagination
import com.kim.ktboot.form.MemberList
import com.kim.ktboot.form.MemberSearchForm
import com.kim.ktboot.orm.jooq.MemberDslRepository
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.MemberRepository
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberDslRepository: MemberDslRepository,
    private val memberRepository: MemberRepository
) {

    /**
     * 특정 회원정보 조회
     */
    fun getMemberOne(id: Long): MemberList {
        return try {
            memberRepository.findByid(id).let{
                MemberList(
                    memberIdx = it.id,
                    memberId = it.memId,
                    memberName = it.memName,
                    memberGender = it.memGender,
                    memberCreatedAt = it.memCreatedAt,
                    memberUpdatedAt = it.memUpdatedAt
                )
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    /**
     * 특정 회원정보 조회
     */
    fun getMemberList(form: MemberSearchForm): ListPagination<MemberList> {
        return try {
            memberDslRepository.getMemberList(form).map{
                MemberList(
                    memberIdx = it.memberIdx,
                    memberId = it.memberId,
                    memberName = it.memberName,
                    memberGender = it.memberGender,
                    memberCreatedAt = it.memberCreatedAt,
                    memberUpdatedAt = it.memberUpdatedAt
                )
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }


    fun findByMemId(id: String): MemberEntity {
        val member = memberRepository.findByMemId(id)
        return member
    }


    fun loadUserByMemberId(username: String): User {
        val member = memberRepository.findByMemId(username)
        return User(member.memId, member.memPassword, emptyList())
    }

    fun existsMember(id: String): Boolean {
        return memberRepository.existsByMemId(id)
    }

    fun findMember(id: String): User {
        val member = memberRepository.findByMemId(id)
        return User(member.memId, member.memPassword, emptyList())
    }

    fun getStoredRefreshToken(loginId: String): String? {
        // DB에서 저장된 리프레시 토큰을 조회하는 로직 구현
        // 리프레시 토큰이 없으면 null 반환
        return memberRepository.findByMemId(loginId).memRefreshToken
    }

    fun deleteAccessToken(loginId: String): String? {
        // DB에서 저장된 리프레시 토큰을 조회하는 로직 구현
        // 리프레시 토큰이 없으면 null 반환
        return memberRepository.deleteMemAccessTokenByMemId(loginId).memAccessToken
    }


    fun deleteRefreshToken(loginId: String): String? {
        // DB에서 저장된 리프레시 토큰을 조회하는 로직 구현
        // 리프레시 토큰이 없으면 null 반환
        return memberRepository.deleteMemRefreshTokenByMemId(loginId).memRefreshToken
    }


    fun save(member: MemberEntity): MemberEntity {
        return memberRepository.save(member)
    }

}