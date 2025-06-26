package com.kim.ktboot.form

import java.math.BigDecimal


data class ProductUpdateForm(
        val productName: String,
        val productPrice: String,
        val productImageUrl: List<String>? = null,
        val productUuid: List<String>? = null
        // 이미지 제외 — MultipartFile로 따로 받음
)

data class ProductCreateForm(
    val productIdx: Long?,
    val productName: String? = null,
    val productPrice: String? = null,
)


data class ProductList(
        val productIdx: Int?,
        val productName: String? = null,
        val productPrice: BigDecimal? = null,
        val productUuid: List<String?>? = null,
        var productImage: List<String?>? = null,
        val productCreatedAt: String? = null,
        val productUpdatedAt: String? = null,
)


data class ProductImageList(
        val productImageIdx: Int?,
        val productImagePrdIdx: Int?,
        val productImageOriginName: String? = null,
        val productName: String? = null,
        val productContentType: String? = null,
        val productCreatedAt: String? = null,
        val productUpdatedAt: String? = null,
)