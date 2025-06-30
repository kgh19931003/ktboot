package com.kim.ktboot.form

import com.fasterxml.jackson.annotation.JsonInclude


data class ProductUpdateForm(
        val productName: String,
        val productPrice: Long,
        val productImageUrl: List<String>? = null,
        val productImageIndex: List<Int>? = null,
        val productImageOriginalIndex: List<Int>? = null,
        val productImageDeleteIndex: List<Int>? = null,
        val productImageMultipartFileOrder: List<Int>? = null,
        val productImageOrder: List<Int>? = null,
        val productImageUuid: List<String>? = null,
        val productImageDeleteUuid: List<String>? = null
        // 이미지 제외 — MultipartFile로 따로 받음
)

data class ProductCreateForm(
    val productIdx: Long?,
    val productName: String? = null,
    val productPrice: String? = null,
)


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductList(
        val productIdx: Int?,
        val productImgIdx: List<Int?>? = null,
        val productImageOriginalIndex: List<Int?>? = null,
        val productImgOrder: List<Int?>? = null,
        val productName: String? = null,
        val productPrice: Long? = null,
        val productImageUuid: List<String?>? = null,
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