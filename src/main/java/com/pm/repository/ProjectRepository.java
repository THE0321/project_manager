package com.pm.repository;

import com.pm.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * 
 * project JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT A " +
            "FROM Project A " +
            "LEFT JOIN FETCH A.projectStatus " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE UPPER(A.title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR A.endDate <= :endDate) " +
            "ORDER BY A.idx DESC")
    List<Project> findByTitleAndStatusIdxAndStartDateAndEndDateOrderByIdxDesc(String title, Long statusIdx, Date startDate, Date endDate);

    @Query("SELECT COUNT(A.idx) " +
            "FROM Project A " +
            "WHERE UPPER(A.title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate) " +
            "AND (:endDate IS NULL OR A.endDate <= :endDate)")
    Long countByTitleAndStatusIdxAndStartDateAndEndDate(String title, Long statusIdx, Date startDate, Date endDate);

    @Query("SELECT A " +
            "FROM Project A " +
            "LEFT JOIN FETCH A.projectStatus " +
            "WHERE A.idx IN (SELECT B.projectIdx FROM ProjectMember B WHERE B.memberIdx = :memberIdx) " +
            "ORDER BY A.title")
    List<Project> findByMemberIdxOrderByTitle(Long memberIdx);
}
