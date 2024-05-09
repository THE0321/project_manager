package com.pm.repository;

import com.pm.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
