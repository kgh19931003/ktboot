package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.model.Response
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.ProductEntity
import com.kim.ktboot.orm.jpa.ProductRepository
import com.kim.ktboot.service.ExcelService
import com.kim.ktboot.service.ProductService
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/product") // API 요청을 위한 기본 경로
class ProductController (
        private val productService: ProductService,
        private val productRepository: ProductRepository,
        private val excelService: ExcelService
){

    @GetMapping("/one/{id}")
    fun productOne(@PathVariable id: Int): ProductList {
        return productService.getProductOne(id)
    }


    @GetMapping("/list")
    fun productList(form: ProductSearchForm): ListPagination<ProductList> {
        return productService.getProductList(form)
    }

    @PostMapping("/create")
    @Transactional
    fun productCreate(@RequestBody form: ProductSearchForm): ProductEntity {
        val productEntity = ProductEntity(
            id = form.productIdx,
            prdName = form.productName,
            prdPrice = form.productPrice
        )

        return productService.save(productEntity)
    }

    @PutMapping("/update/{id}")
    @Transactional
    fun productUpdate(@PathVariable id: Int, @RequestBody form: ProductSearchForm): ProductEntity {
        // 직접 객체의 필드를 수정
        val product = productRepository.findByid(id).copy(
            id = id,
            prdName = form.productName,
            prdPrice = form.productPrice,
            prdUpdatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        // 수정된 객체를 저장
        return productService.save(product)
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    fun productDelete(@PathVariable id: Int): Response<String> {
        return try {
            productRepository.deleteById(id)
            Response.success("회원 삭제 성공")
        } catch (ex: Exception) {
            Response.fail("회원 삭제 실패: ${ex.message}")
        }
    }



    @GetMapping("/excel")
    fun downloadProductListExcel(
            @ModelAttribute form: ProductSearchForm,
            response: HttpServletResponse
    ) {
        excelService.productExcelDownload(productService.getProductList(form), response, "상품목록")
    }
}
