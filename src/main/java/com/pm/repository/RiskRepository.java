package com.pm.repository;

import com.pm.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * risk JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface RiskRepository extends JpaRepository<Risk, Long> {
    @Query("SELECT COUNT(A.idx) " +
            "FROM Risk A " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND UPPER(A.title) LIKE CONCAT('%', :title, '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) ")
    Long countByTitleContainingAndStatusIdxOrderByIdxDesc(Long projectIdx, String title, Long statusIdx);

    @Query("SELECT A " +
            "FROM Risk A " +
            "LEFT JOIN FETCH A.issueStatus " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND UPPER(A.title) LIKE CONCAT('%', :title, '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "ORDER BY A.idx DESC ")
    List<Risk> findByTitleContainingAndStatusIdxOrderByIdxDesc(Long projectIdx, String title, Long statusIdx);

    @Query("SELECT A " +
            "FROM Risk A " +
            "LEFT JOIN A.issueStatus " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND A.registDate >= :startDate " +
            "ORDER BY A.idx DESC")
    List<Risk> findByProjectIdxRecently(Long projectIdx, Timestamp startDate);
}
