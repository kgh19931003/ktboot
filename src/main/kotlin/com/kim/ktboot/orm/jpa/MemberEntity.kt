package com.kim.ktboot.orm.jpa

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "member", schema = "test")
data class MemberEntity(
    @Id
    @Column(name = "mem_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 ID 생성 설정
    var id: Long? = null,

    @Size(max = 50)
    @Column(name = "mem_id", length = 50)
    var memId: String? = null,

    @Size(max = 255)
    @Column(name = "mem_password")
    var memPassword: String? = null,

    @Size(max = 50)
    @Column(name = "mem_name", length = 50)
    var memName: String? = null,

    @Size(max = 5)
    @Column(name = "mem_gender", length = 5)
    var memGender: String? = null,

    @Size(max = 50)
    @Column(name = "mem_created_at", length = 50)
    var memCreatedAt: String? = null,

    @Size(max = 50)
    @Column(name = "mem_updated_at", length = 50)
    var memUpdatedAt: String? = null,

    @Size(max = 50)
    @Column(name = "mem_deleted_at", length = 50)
    var memDeletedAt: String? = null,

    @Size(max = 255)
    @Column(name = "mem_access_token")
    var memAccessToken: String? = null,

    @Size(max = 255)
    @Column(name = "mem_refresh_token")
    var memRefreshToken: String? = null

) {

}