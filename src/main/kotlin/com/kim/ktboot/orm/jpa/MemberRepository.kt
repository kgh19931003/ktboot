package com.kim.ktboot.orm.jpa

import com.kim.ktboot.orm.jpa.MemberEntity
import org.jooq.impl.QOM.Uuid
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun existsByMemId(memId: String): Boolean

    fun findByid(id: Long): MemberEntity

    fun findByMemId(memId: String): MemberEntity

    fun findByMemIdAndMemRefreshToken(memId: String, memUuid: String): MemberEntity


    override fun deleteById(id: Long)

    fun deleteMemAccessTokenByMemId(memId: String): MemberEntity

    fun deleteMemRefreshTokenByMemId(memId: String): MemberEntity
}