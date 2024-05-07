package com.pm.repository;

import com.pm.entity.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 *
 * action_item JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {
    @Query("SELECT A " +
            "FROM ActionItem A " +
            "LEFT JOIN FETCH A.actionItemStatus " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND (:directoryIdx IS NULL OR A.directoryIdx = :directoryIdx) " +
            "AND UPPER(:title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate)" +
            "AND (:endDate IS NULL OR A.endDate <= :endDate) " +
            "ORDER BY A.idx DESC ")
    List<ActionItem> findByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate);

    @Query("SELECT COUNT(A.idx) " +
            "FROM ActionItem A " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND (:directoryIdx IS NULL OR A.directoryIdx = :directoryIdx) " +
            "AND UPPER(:title) LIKE CONCAT('%', UPPER(:title), '%') " +
            "AND (:statusIdx IS NULL OR A.statusIdx = :statusIdx) " +
            "AND (:startDate IS NULL OR A.startDate >= :startDate)" +
            "AND (:endDate IS NULL OR A.endDate <= :endDate)")
    Long countByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate);

    @Query("SELECT A " +
            "FROM ActionItem A " +
            "WHERE A.projectIdx = :projectIdx " +
            "AND UPPER(A.title) LIKE CONCAT('%', :title, '%') " +
            "ORDER BY A.title")
    List<ActionItem> findByProjectIdxAndTitleContainsOrderByTitle(Long projectIdx, String title);
}
