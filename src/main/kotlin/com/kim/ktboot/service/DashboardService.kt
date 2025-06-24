package com.kim.ktboot.service



import com.kim.ktboot.form.ListPagination
import com.kim.ktboot.form.MemberList
import com.kim.ktboot.form.MemberSearchForm
import com.kim.ktboot.orm.jooq.DashboardDslRepository
import com.kim.ktboot.orm.jooq.MemberDslRepository
import com.kim.ktboot.orm.jpa.MemberEntity
import com.kim.ktboot.orm.jpa.MemberRepository
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class DashboardService(
    private val dashboardDslRepository: DashboardDslRepository
) {

    /**
     * 저번달과 비교한 신규회원자수
     */
    fun todayNewMemberCount(): Int {
        return try {
            dashboardDslRepository.getTodayNewMemberCount()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }


    /**
     * 저번달과 비교한 신규회원자수 퍼센테이지
     */
    fun monthlyNewMemberStats(): Int {
        return try {
            dashboardDslRepository.getMonthlyNewMemberStats().let{
                (thisMonth, lastMonth) ->
                if (lastMonth == 0) 100 else ((thisMonth - lastMonth).toDouble() / lastMonth * 100).roundToInt()
            }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }


    /**
     * 신규가입자 월별 차트
     */
    fun monthlyMemberStatsJson(): List<Map<String, Any>> {
        return try {
            dashboardDslRepository.getMonthlyMemberStatsJson()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }


}