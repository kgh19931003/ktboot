package com.kim.ktboot.orm.jpa

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "product", schema = "test", catalog = "")
data class ProductEntity(

        @Id
        @Column(name = "prd_idx")
        @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 ID 생성 설정
        var id: Int? = null,

        @Column(name = "prd_name", nullable = true)
        var prdName: String? = null,

        @Column(name = "prd_price", nullable = true)
        var prdPrice: BigDecimal? = null,

        @Column(name = "prd_thumbnail", nullable = true)
        var prdThumbnail: String? = null,

        @Column(name = "prd_created_at", nullable = true)
        var prdCreatedAt: String? = null,

        @Column(name = "prd_updated_at", nullable = true)
        var prdUpdatedAt: String? = null,

        @Column(name = "prd_deleted_at", nullable = true)
        var prdDeletedAt: String? = null,

        ){
}

