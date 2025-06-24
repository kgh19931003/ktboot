package com.kim.ktboot.orm.jooq


import com.kim.ktboot.form.ListPagination
import com.kim.ktboot.form.MemberList
import com.kim.ktboot.form.MemberSearchForm
import com.kim.ktboot.jooq.portfolio.tables.references.MEMBER
import com.kim.ktboot.proto.isNotNull
import com.kim.ktboot.proto.`when`
import org.jooq.*
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
class MemberDslRepository(
    private val dsl: DSLContext,
) {
    fun getMemberQuery(form: MemberSearchForm): SelectConditionStep<Record6<Long?, String?, String?, String?, String?, String?>> {
        return dsl.select(
                MEMBER.MEM_IDX.`as`("memberIdx"),
                MEMBER.MEM_ID.`as`("memberId"),
                MEMBER.MEM_NAME.`as`("memberName"),
                MEMBER.MEM_GENDER.`as`("memberGender"),
                MEMBER.MEM_CREATED_AT.`as`("memberCreatedAt"),
                MEMBER.MEM_UPDATED_AT.`as`("memberUpdatedAt")
            )
            .from(MEMBER)
            .where(DSL.noCondition())
            .`when`(form.memberId.isNotNull(), MEMBER.MEM_ID.eq(form.memberId))
            .`when`(form.memberName.isNotNull(), MEMBER.MEM_NAME.eq(form.memberName))
            .`when`(form.memberGender.isNotNull(), MEMBER.MEM_GENDER.eq(form.memberGender))
    }


    fun getMemberOne(form: MemberSearchForm): List<MemberList> {
        return getMemberQuery(form).fetch { it.into(MemberList::class.java) }
    }

    fun getMemberList(form: MemberSearchForm): ListPagination<MemberList> {
        val query = getMemberQuery(form)
        return ListPagination.of(dsl, query, form) { record ->
            record.into(MemberList::class.java)
        }
    }

}