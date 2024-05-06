package com.pm.repository;

import com.pm.entity.TestCaseMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 
 * test_case_member JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
public interface TestCaseMemberRepository extends JpaRepository<TestCaseMember, Long> {
    @Query("SELECT A " +
            "FROM TestCaseMember A " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.testCaseIdx = :testCaseIdx")
    List<TestCaseMember> findByTestCaseIdx(Long testCaseIdx);
}
