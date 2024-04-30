package com.pm.repository;

import com.pm.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

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
    List<ProjectMember> findByProjectIdxOrderByIdxDesc(Long projectIdx);
}
