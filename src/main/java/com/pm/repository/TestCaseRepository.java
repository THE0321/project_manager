package com.pm.repository;

import com.pm.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 *
 * test_case JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    @Query("SELECT A " +
            "FROM TestCase A " +
            "LEFT JOIN FETCH A.testCaseStatus " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND (:directoryIdx IS NULL OR A.directoryIdx = :directoryIdx) " +
            "AND UPPER(:title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate)" +
            "AND (:endDate IS NULL OR A.endDate <= :endDate) " +
            "ORDER BY A.idx DESC ")
    List<TestCase> findByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate);

    @Query("SELECT COUNT(A.idx) " +
            "FROM TestCase A " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND (:directoryIdx IS NULL OR A.directoryIdx = :directoryIdx) " +
            "AND UPPER(:title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate)" +
            "AND (:endDate IS NULL OR A.endDate <= :endDate) ")
    Long countByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate);
}
