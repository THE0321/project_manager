package com.pm.repository;

import com.pm.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * issue JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT COUNT(A.idx) " +
            "FROM Issue A " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND UPPER(A.title) LIKE CONCAT('%', :title, '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) ")
    Long countByTitleContainingAndStatusIdxOrderByIdxDesc(Long projectIdx, String title, Long statusIdx);

    @Query("SELECT A " +
            "FROM Issue A " +
            "LEFT JOIN FETCH A.issueStatus " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND UPPER(A.title) LIKE CONCAT('%', :title, '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "ORDER BY A.idx DESC ")
    List<Issue> findByTitleContainingAndStatusIdxOrderByIdxDesc(Long projectIdx, String title, Long statusIdx);
}
