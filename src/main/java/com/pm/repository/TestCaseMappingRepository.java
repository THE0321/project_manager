package com.pm.repository;

import com.pm.entity.TestCaseMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * test_case_mapping JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
public interface TestCaseMappingRepository extends JpaRepository<TestCaseMapping, Long> {
    @Query("SELECT A " +
            "FROM TestCaseMapping A " +
            "LEFT JOIN FETCH A.testCase " +
            "WHERE A.testCaseIdx = :testCaseIdx")
    List<TestCaseMapping> findByTestCaseIdx(Long testCaseIdx);
}
