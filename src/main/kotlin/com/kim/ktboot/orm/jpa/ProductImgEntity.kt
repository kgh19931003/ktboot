package com.kim.ktboot.orm.jpa

import jakarta.persistence.*
import org.checkerframework.common.aliasing.qual.Unique
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "product_img", schema = "test", catalog = "")
data class ProductImgEntity (

    @Id
    @Column(name = "prdi_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 ID 생성 설정
    var id: Int? = null,

    @Column(name = "prdi_prd_idx", nullable = true)
    var prdiPrdIdx: Int? = null,

    @Column(name = "prdi_origin_name", nullable = true)
    var prdiOriginName: String? = null,

    @Column(name = "prdi_name", nullable = true)
    var prdiName: String? = null,

    @Column(name = "prdi_dir", nullable = true)
    var prdiDir: String? = null,

    @Column(name = "prdi_src", nullable = true)
    var prdiSrc: String? = null,

    @Column(name = "prdi_size", nullable = true)
    var prdiSize: Double? = null,

    @Column(name = "prdi_content_type", nullable = true)
    var prdiContentType: String? = null,

    @Column(name = "prdi_order", nullable = true)
    var prdiOrder: Int? = null,

    @Column(name = "prdi_uuid", nullable = true)
    var prdiUuid: String? = null,

    @CreatedDate
    @Column(name = "prdi_created_at", nullable = true)
    var prdiCreatedAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "prdi_updated_at", nullable = true)
    var prdiUpdatedAt: LocalDateTime? = null,

    @Column(name = "prdi_deletd_at", nullable = true)
    var prdiDeletdAt: LocalDateTime? = null


)
{}

