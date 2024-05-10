package com.pm.repository;

import com.pm.entity.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * schedule_member DTO Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {
    @Query("SELECT A " +
            "FROM ScheduleMember A " +
            "LEFT JOIN FETCH A.schedule " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.scheduleIdx = :scheduleIdx")
    List<ScheduleMember> findByScheduleIdx(Long scheduleIdx);
}
