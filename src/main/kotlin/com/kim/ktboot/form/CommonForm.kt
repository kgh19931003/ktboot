package com.kim.ktboot.form

import java.math.BigDecimal
import java.time.LocalDate

data class ApiResponse(
        val success: Boolean,
        val message: String
)


data class MemberSearchForm(
        var fromDate: LocalDate? = null,
        var toDate: LocalDate? = null,
        var memberIdx: Int? = null,
        var memberId: String? = null,
        var memberPassword: String? = null,
        var memberName: String? = null,
        var memberGender: String? = null
) : ListForm()


data class ProductSearchForm(
        var fromDate: LocalDate? = null,
        var toDate: LocalDate? = null,
        var productIdx: Int? = null,
        var productName: String? = null,
        var productPrice: BigDecimal? = null
) : ListForm()



data class LoginRequest(
        val id: String,
        val pass: String
)