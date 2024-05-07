package com.pm.repository;

import com.pm.entity.RiskMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * risk_mapping JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface RiskMappingRepository extends JpaRepository<RiskMapping, Long> {
    @Query("SELECT A " +
            "FROM RiskMapping A " +
            "LEFT JOIN FETCH A.actionItem " +
            "LEFT JOIN FETCH A.testCase " +
            "WHERE A.risk.idx = :riskIdx")
    List<RiskMapping> findByRiskIdx(Long riskIdx);

    @Query("SELECT A " +
            "FROM RiskMapping A " +
            "LEFT JOIN FETCH A.risk " +
            "WHERE A.actionItem.idx = :riskIdx")
    List<RiskMapping> findByActionItemIdx(Long riskIdx);

    @Query("SELECT A " +
            "FROM RiskMapping A " +
            "LEFT JOIN FETCH A.risk " +
            "WHERE A.testCase.idx = :riskIdx")
    List<RiskMapping> findByTestCaseIdx(Long riskIdx);
}
