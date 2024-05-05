package com.pm.repository;

import com.pm.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
