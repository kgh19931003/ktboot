package com.kim.ktboot.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
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