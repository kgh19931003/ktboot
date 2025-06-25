package com.kim.ktboot.orm.jooq


import com.kim.ktboot.form.*
import com.kim.ktboot.jooq.portfolio.tables.references.MEMBER
import com.kim.ktboot.jooq.portfolio.tables.references.PRODUCT
import com.kim.ktboot.jooq.portfolio.tables.references.PRODUCT_IMG
import com.kim.ktboot.proto.isNotNull
import com.kim.ktboot.proto.`when`
import org.jooq.*
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class ProductDslRepository(
    private val dsl: DSLContext,
) {
    fun getProductQuery(form: ProductSearchForm): SelectConditionStep<Record5<Int?, String?, BigDecimal?, String?, String?>> {
        return dsl.select(
                PRODUCT.PRD_IDX.`as`("productIdx"),
                PRODUCT.PRD_NAME.`as`("productName"),
                PRODUCT.PRD_PRICE.`as`("productPrice"),
                PRODUCT.PRD_CREATED_AT.`as`("productCreatedAt"),
                PRODUCT.PRD_UPDATED_AT.`as`("productUpdatedAt")
            )
            .from(PRODUCT)
            .where(DSL.noCondition())
            .`when`(form.productIdx.isNotNull(), PRODUCT.PRD_IDX.eq(form.productIdx))
            .`when`(form.productName?.isNotBlank() == true, PRODUCT.PRD_NAME.like("%${form.productName}%"))
            .`when`(form.productPrice.isNotNull(), PRODUCT.PRD_PRICE.eq(form.productPrice))
    }


    fun getProductOne(form: ProductSearchForm): List<ProductList> {
        return getProductQuery(form).fetch { it.into(ProductList::class.java) }
    }

    fun getProductList(form: ProductSearchForm): ListPagination<ProductList> {
        val query = getProductQuery(form)
        return ListPagination.of(dsl, query, form) { record ->
            record.into(ProductList::class.java)
        }
    }

}