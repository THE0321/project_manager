package com.pm.repository;

import com.pm.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * project_status JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
}
