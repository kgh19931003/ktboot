package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.model.Response
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.MemberRepository
import com.kim.ktboot.service.DashboardService
import com.kim.ktboot.service.ExcelService
import com.kim.ktboot.service.MemberService
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.apache.poi.ss.formula.functions.T
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/dashboard") // API 요청을 위한 기본 경로
class DashboardController (
    private val dashboardService: DashboardService
){


    @GetMapping("/mem-new")
    fun momNew(): Int {
        return dashboardService.todayNewMemberCount()
    }

    @GetMapping("/mem-mom-stats")
    fun momNewMemberStats(): Int {
        return dashboardService.monthlyNewMemberStats()
    }

    @GetMapping("/mem-mom-stat-json")
    fun momNewMemberStatsJson(): List<Map<String, Any>> {
        return dashboardService.monthlyMemberStatsJson()
    }


}
