package com.kim.ktboot

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
@EnableEncryptableProperties
class KtbootApplication

fun main(args: Array<String>) {
    //runApplication<RefitApplication>(*args)

    val app = SpringApplication(com.kim.ktboot.KtbootApplication::class.java)

    // OS 이름 확인
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    if (osName.contains("win")) {
        // 윈도우면 'windows' 프로파일 활성화
        app.setAdditionalProfiles("windows")

        // 그리고 서버 포트 80으로 덮어쓰기
        val props: MutableMap<String, Any> = HashMap()
        props["server.port"] = 80
        app.setDefaultProperties(props)
    }

    app.run(*args)
}
