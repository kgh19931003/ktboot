package com.kim.ktboot.orm.jpa

import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImgRepository : JpaRepository<ProductImgEntity, Int> {
    fun existsByid(id: Int): Boolean

    fun findByid(id: Int): ProductImgEntity

    fun findByPrdiPrdIdx(id: Int): List<ProductImgEntity>

    fun findByPrdiPrdIdxOrderByPrdiOrderAsc(id: Int): List<ProductImgEntity>
    fun findByPrdiUuid(uuid: String?): ProductImgEntity?

    fun findByPrdiUuidAndPrdiOrder(uuid: String?, index: Int?): ProductImgEntity?

    override fun <S : ProductImgEntity?> save(entity: S): S
    override fun deleteById(id: Int)

}