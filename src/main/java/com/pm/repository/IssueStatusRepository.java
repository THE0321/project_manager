package com.pm.repository;

import com.pm.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * issue_status JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {
}
