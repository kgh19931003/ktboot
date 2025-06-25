package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.model.Response
import com.kim.ktboot.orm.jpa.*
import com.kim.ktboot.proto.combine
import com.kim.ktboot.service.ExcelService
import com.kim.ktboot.service.ProductService
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import nowAsRegularFormat
import nowAsTimestamp
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/product") // API 요청을 위한 기본 경로
class ProductController (
        private val productService: ProductService,
        private val productRepository: ProductRepository,
        private val productImgRepository: ProductImgRepository,
        private val excelService: ExcelService
){

    @GetMapping("/one/{id}")
    fun productOne(@PathVariable id: Int): ProductList {
        return productService.getProductOne(id)
    }

    @GetMapping("/image-one/{prdId}")
    fun productImageOne(@PathVariable prdId: Int): ProductImageList {
        return productService.getProductImageOne(prdId)
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

    @PostMapping("/update/{id}")
    @Transactional
    fun productUpdate(
            @PathVariable id: Int,
            @RequestPart("form") form: ProductUpdateForm,
            @RequestPart("productImage", required = false) files: List<MultipartFile>?
    ): ProductEntity {
        val product = productRepository.findByid(id).copy(
                id = id,
                prdName = form.productName,
                prdPrice = form.productPrice.toBigDecimal(),
                prdUpdatedAt = nowAsRegularFormat()
        )


        productService.save(product).let{
            files?.filterNot { it.isEmpty }?.forEach { file ->
                val originalName = file.originalFilename ?: "unknown.png"
                val extension = file.originalFilename?.substringAfterLast('.', "") ?: "png"
                val savedName = nowAsTimestamp().combine(".$extension")

                val uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "product", "images")

                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir)
                }

                val targetPath = uploadDir.resolve(savedName!!)
                file.transferTo(targetPath.toFile()) // 실제 저장

                // DB 저장
                productImgRepository.save(
                        ProductImgEntity(
                                prdiPrdIdx = id,
                                prdiOriginName = originalName,
                                prdiName = savedName,
                                prdiContentType = file.contentType?.substringAfter("/") ?: extension
                        )
                )
            }
        }

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
