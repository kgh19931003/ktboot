package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.model.Response
import com.kim.ktboot.orm.jpa.*
import com.kim.ktboot.proto.combine
import com.kim.ktboot.proto.isNotNull
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
import java.util.*
import kotlin.io.path.pathString

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
        return productService.getProductOne(id).let{ prdInfo ->
            var productImage = productService.getProductImageOne(id)
            prdInfo.copy(productImage = productImage.map { it.prdiSrc }, productUuid = productImage.map { it.prdiUuid })
        }
    }

    @GetMapping("/image-one/{prdId}")
    fun productImageOne(@PathVariable prdId: Int): List<ProductImgEntity> {
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

        var productImageInfo = productService.getProductImageOne(id)


        productService.save(product).let{

            println("form.productImage"+ form)

            // 이미 등록되어있는 파일 정렬, 삭제
            form.productImageUrl?.forEachIndexed{ index, value ->
                val uuid = form.productUuid?.getOrNull(index)
                val imagePath = value.substringBeforeLast("/")
                val imageName = value.substringAfterLast("/")

                println("uuid : "+ uuid)
                println("imagePath : "+ imagePath)
                println("imageName : "+ imageName)
            }

            // 신규로 등록되는 실제 파일
            files?.filterNot { it.isEmpty }?.forEachIndexed  { index, file ->
                val originalName = file.originalFilename ?: "unknown.png"
                val extension = file.originalFilename?.substringAfterLast('.', "") ?: "png"
                val savedName = nowAsTimestamp().combine(".$extension")
                val root = System.getProperty("user.dir")
                val uploadDir = Paths.get(root, "uploads", "product", "images")
                val relativePath = uploadDir.toString().removePrefix(root).replace("\\", "/")
                val src = relativePath.combine("/"+savedName!!)
                val imageLength = productImageInfo.size
                val orderIndex = imageLength.plus(index)

                    // 신규 파일 저장
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir)
                }

                val targetPath = uploadDir.resolve(savedName)
                file.transferTo(targetPath.toFile())

                productImgRepository.save(
                        ProductImgEntity(
                                prdiPrdIdx = id,
                                prdiOriginName = originalName,
                                prdiName = savedName,
                                prdiDir = relativePath,
                                prdiSrc = src,
                                prdiOrder = orderIndex,
                                prdiContentType = file.contentType?.substringAfter("/") ?: extension,
                                prdiUuid = UUID.randomUUID().toString()
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
