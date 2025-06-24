package com.kim.ktboot.form

import java.math.BigDecimal


data class ProductCreateForm(
    val productIdx: Long?,
    val productName: String? = null,
    val productPrice: String? = null,
)


data class ProductList(
        val productIdx: Int?,
        val productName: String? = null,
        val productPrice: BigDecimal? = null,
        val productCreatedAt: String? = null,
        val productUpdatedAt: String? = null,
)