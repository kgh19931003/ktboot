package com.kim.ktboot.controller


import com.kim.ktboot.form.*
import com.kim.ktboot.model.Response
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.MemberRepository
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
@RequestMapping("/member") // API 요청을 위한 기본 경로
class MemberController (
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val excelService: ExcelService
){

    @GetMapping("/one/{id}")
    fun memberOne(@PathVariable id: Long): MemberList {
        return memberService.getMemberOne(id)
    }


    @GetMapping("/list")
    fun memberList(form: MemberSearchForm): ListPagination<MemberList> {
        return memberService.getMemberList(form)
    }

    @PostMapping("/create")
    @Transactional
    fun memberCreate(@RequestBody form: MemberCreateForm): MemberEntity {
        val memberEntity = MemberEntity(
            memId = form.memberId,
            memPassword = passwordEncoder.encode(form.memberPassword),
            memName = form.memberName,
            memGender = form.memberGender,
            memCreatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )

        return memberService.save(memberEntity)
    }

    @PutMapping("/update/{id}")
    @Transactional
    fun memberUpdate(@PathVariable id: Long, @RequestBody form: MemberSearchForm): MemberEntity {
        // 직접 객체의 필드를 수정
        val member = memberRepository.findByMemId(form.memberId!!).copy(
            id = id,
            memPassword = passwordEncoder.encode(form.memberPassword),
            memName = form.memberName,
            memGender = form.memberGender,
            memUpdatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
        // 수정된 객체를 저장
        return memberService.save(member)
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    fun memberDelete(@PathVariable id: Long): Response<String> {
        return try {
            memberRepository.deleteById(id)
            Response.success("회원 삭제 성공")
        } catch (ex: Exception) {
            Response.fail("회원 삭제 실패: ${ex.message}")
        }
    }



    @GetMapping("/excel")
    fun downloadUserListExcel(
            @ModelAttribute form: MemberSearchForm,
            response: HttpServletResponse
    ) {
        excelService.memberExcelDownload(memberService.getMemberList(form), response, "회원목록")
    }
}
