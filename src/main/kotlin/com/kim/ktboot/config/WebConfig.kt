package com.kim.ktboot.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString()

        registry.addResourceHandler("/uploads/**") // URL 패턴
                .addResourceLocations(uploadPath)      // 실제 로컬 폴더
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        // /api/** 경로는 Spring Boot에서 처리되도록 제외
        registry.addViewController("/{path:^(?!api)(?!.*\\.).*$}")
            .setViewName("forward:/index.html")

        // /edit/{id}/{other} 형태의 경로 처리 (2개 이상의 세그먼트 포함)
        registry.addViewController("/edit/{other:.*}")
            .setViewName("forward:/index.html")

    }
}