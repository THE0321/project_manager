package com.pm.repository;

import com.pm.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * project_member JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    @Query("SELECT A " +
            "FROM ProjectMember A " +
            "LEFT JOIN FETCH A.team " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.projectIdx = :projectIdx " +
            "ORDER BY A.idx DESC")
    List<ProjectMember> findByProjectIdxOrderByIdxDesc(Long projectIdx);
}
