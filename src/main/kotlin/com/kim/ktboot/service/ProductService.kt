package com.kim.ktboot.service

import com.kim.ktboot.form.*
import com.kim.ktboot.orm.jooq.ProductDslRepository
import com.kim.ktboot.orm.jpa.ProductEntity
import com.kim.ktboot.orm.jpa.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
        private val productDslRepository: ProductDslRepository,
        private val productRepository: ProductRepository
) {

    /**
     * 특정 회원정보 조회
     */
    fun getProductOne(id: Int): ProductList {
        return try {
            productRepository.findByid(id).let{
                ProductList(
                    productIdx = it.id,
                    productName = it.prdName,
                    productPrice = it.prdPrice,
                    productCreatedAt = it.prdCreatedAt
                )
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    /**
     * 특정 회원정보 조회
     */
    fun getProductList(form: ProductSearchForm): ListPagination<ProductList> {
        return try {
            productDslRepository.getProductList(form).map{
                ProductList(
                    productIdx = it.productIdx,
                    productName = it.productName,
                    productPrice = it.productPrice,
                    productCreatedAt = it.productCreatedAt
                )
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }



    fun save(product: ProductEntity): ProductEntity {
        return productRepository.save(product)
    }

}