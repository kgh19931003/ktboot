package com.kim.ktboot.service

import com.kim.ktboot.form.ListPagination
import com.kim.ktboot.form.MemberList
import com.kim.ktboot.orm.jpa.MemberRepository
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.net.URLEncoder

@Service
class ExcelService() {

    fun responseSetting(response: HttpServletResponse, fileName: String): HttpServletResponse {
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        // val fileName = URLEncoder.encode(fileName+".xlsx", "UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''${URLEncoder.encode(fileName+".xlsx", "UTF-8")}")
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition")

        return response
    }

    fun memberExcelDownload(data: ListPagination<MemberList>, response: HttpServletResponse, fileName: String) {

        // ✔ 매 요청마다 새로운 워크북 생성
        XSSFWorkbook().use { workbook ->
            val sheet = workbook.createSheet(fileName)

            // 헤더
            val header = sheet.createRow(0)
            header.createCell(0).setCellValue("ID")
            header.createCell(1).setCellValue("이름")
            header.createCell(2).setCellValue("성별")
            header.createCell(3).setCellValue("가입일자")

            // 데이터 채우기
            data.contents.forEachIndexed { index, value ->
                val row = sheet.createRow(index + 1)
                row.createCell(0).setCellValue(value.memberId)
                row.createCell(1).setCellValue(value.memberName)
                row.createCell(2).setCellValue(value.memberGender)
                row.createCell(3).setCellValue(value.memberCreatedAt)
            }

            // 응답 헤더 설정
            responseSetting(response, fileName)

            // 배열 쓰기 → 스트림 flush → close 자동 실행
            workbook.write(response.outputStream)
            // `use` 블록 종료 시 자동 close()
        }

    }

}