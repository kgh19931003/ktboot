package com.kim.ktboot.form

import java.time.LocalDate

data class ApiResponse(
        val success: Boolean,
        val message: String
)


data class SearchForm(
        var fromDate: LocalDate? = null,
        var toDate: LocalDate? = null,
        var memberIdx: Int? = null,
        var memberId: String? = null,
        var memberPassword: String? = null,
        var memberName: String? = null,
        var memberGender: String? = null
) : ListForm()


data class LoginRequest(
        val id: String,
        val pass: String
)