package com.kim.ktboot.orm.jpa

import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductImgRepository : JpaRepository<ProductImgEntity, Int> {
    fun existsByid(id: Int): Boolean

    fun findByid(id: Int): ProductImgEntity?
    fun findByidAndPrdiOrder(id: Int, order: Int?): ProductImgEntity?

    fun findByPrdiPrdIdx(id: Int): List<ProductImgEntity>

    fun findByPrdiPrdIdxOrderByPrdiOrderAsc(id: Int): List<ProductImgEntity>

    @Modifying
    @Query(
            "UPDATE ProductImgEntity pi " +
                    "SET pi.prdiOrder = pi.prdiOrder - 1 " +
                    "WHERE pi.prdiOrder > :order AND pi.prdiPrdIdx = :prd_idx"
    )
    fun decrementOrderGreaterThan(
            @Param("prd_idx") prd_idx: Int,
            @Param("order") order: Int?
    ): Int

    override fun <S : ProductImgEntity?> save(entity: S): S
    override fun deleteById(id: Int)


}