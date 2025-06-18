package com.kim.ktboot.form

import java.math.BigDecimal


data class MemberCreateForm(
    var memberId: String,
    var memberPassword: String,
    var memberName: String,
    var memberGender: String
)


data class MemberList(
    val memberIdx: Long?,
    val memberId: String? = null,
    val memberName: String? = null,
    val memberGender: String? = null,
    val memberCreatedAt: String? = null,
    val memberUpdatedAt: String? = null,
)