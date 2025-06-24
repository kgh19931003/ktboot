package com.kim.ktboot.controller

import UploadedFileInfo
import com.kim.ktboot.model.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@RestController
@RequestMapping("/upload") // API 요청을 위한 기본 경로
class UploadController {

    @PostMapping("/test")
    fun uploadFiles(@RequestParam("files") files: List<MultipartFile>): Response<List<UploadedFileInfo>> {
        val uploadDir = Path.of("uploads")
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }

        val savedFiles = files.map { file ->
            val filename = file.originalFilename ?: "unnamed"
            val targetPath = uploadDir.resolve(filename)

            // 실제 저장
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING)
            }

            UploadedFileInfo(
                    name = filename,
                    size = file.size,
                    path = targetPath.toString()
            )
        }

        return Response.success(savedFiles)
    }
}
