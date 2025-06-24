package com.kim.ktboot.orm.jpa

import jakarta.persistence.*


@Entity
@Table(name = "product_img", schema = "test", catalog = "")
data class ProductImgEntity (

    @Id
    @Column(name = "prdi_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 ID 생성 설정
    var id: Long? = null,

    @get:Column(name = "prdi_src", nullable = true)
    var prdiSrc: String? = null,

    @get:Column(name = "prdi_size", nullable = true)
    var prdiSize: Double? = null,

    @get:Column(name = "prdi_created_at", nullable = true)
    var prdiCreatedAt: String? = null,

    @get:Column(name = "prdi_updated_at", nullable = true)
    var prdiUpdatedAt: String? = null,

    @get:Column(name = "prdi_deletd_at", nullable = true)
    var prdiDeletdAt: String? = null


)
{}

