package com.pm.repository;

import com.pm.dto.Calendar;
import com.pm.dto.CalendarDto;
import com.pm.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

/**
 *
 * schedule JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(nativeQuery = true, value =
            "SELECT YEAR(schedule_date) AS `year`, MONTH(schedule_date) AS `month`, DAY(schedule_date) AS `day` " +
            "FROM schedule  " +
            "WHERE delete_yn = 'N' " +
            "GROUP BY YEAR(schedule_date), MONTH(schedule_date), DAY(schedule_date)")
    List<Calendar> getCalendarList();

    @Query("SELECT A " +
            "FROM Schedule A " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE A.registerMember.idx = :memberIdx " +
            "AND A.scheduleDate = :scheduleDate " +
            "ORDER BY A.scheduleDate")
    List<Schedule> findByMemberIdx(Date scheduleDate, Long memberIdx);
}
