package com.kim.ktboot.orm.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImgRepository : JpaRepository<ProductImgEntity, Int> {
    fun existsByid(id: Int): Boolean

    fun findByid(id: Int): ProductImgEntity

    override fun <S : ProductImgEntity?> save(entity: S): S
    override fun deleteById(id: Int)

}