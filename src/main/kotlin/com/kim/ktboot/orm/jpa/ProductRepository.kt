package com.kim.ktboot.orm.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Int> {
    fun existsByid(id: Int): Boolean

    fun findByid(id: Int): ProductEntity

    override fun deleteById(id: Int)

}