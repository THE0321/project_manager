package com.pm.repository;

import com.pm.entity.IssueMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * issue_mapping JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface IssueMappingRepository extends JpaRepository<IssueMapping, Long> {
    @Query("SELECT A " +
            "FROM IssueMapping A " +
            "LEFT JOIN FETCH A.testCase " +
            "LEFT JOIN FETCH A.actionItem " +
            "WHERE A.issue.idx = :issueIdx")
    List<IssueMapping> findByIssueIdx(Long issueIdx);

    @Query("SELECT A " +
            "FROM IssueMapping A " +
            "INNER JOIN FETCH A.issue " +
            "WHERE A.testCase.idx = :testCaseIdx")
    List<IssueMapping> findByTestCaseIdx(Long testCaseIdx);

    @Query("SELECT A " +
            "FROM IssueMapping A " +
            "INNER JOIN FETCH A.issue " +
            "WHERE A.actionItem.idx = :actionItemIdx")
    List<IssueMapping> findByActionItemIdx(Long actionItemIdx);
}
