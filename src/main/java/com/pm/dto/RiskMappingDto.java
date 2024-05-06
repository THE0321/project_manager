package com.pm.dto;

import com.pm.entity.*;
import lombok.*;

import java.sql.Timestamp;

/**
 * 
 * risk_mapping DTO
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-06
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-06          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RiskMappingDto {
    private Long idx;
    private Long actionItemIdx;
    private String actionItemTitle;
    private Long testCaseIdx;
    private String testCaseTitle;
    private Long riskIdx;
    private String riskTitle;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public RiskMapping toEntity() {
        ActionItem actionItem = actionItemIdx == null ? null : ActionItem.builder().idx(actionItemIdx).build();
        TestCase testCase = testCaseIdx == null ? null : TestCase.builder().idx(testCaseIdx).build();
        Risk risk = riskIdx == null ? null : Risk.builder().idx(riskIdx).build();

        return RiskMapping.builder()
                .idx(idx)
                .actionItemIdx(actionItemIdx)
                .actionItem(actionItem)
                .testCaseIdx(testCaseIdx)
                .testCase(testCase)
                .riskIdx(riskIdx)
                .risk(risk)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public RiskMappingDto(Long idx, Long actionItemIdx, String actionItemTitle, Long testCaseIdx, String testCaseTitle, Long riskIdx, String riskTitle, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItemTitle = actionItemTitle;
        this.testCaseIdx = testCaseIdx;
        this.testCaseTitle = testCaseTitle;
        this.riskIdx = riskIdx;
        this.riskTitle = riskTitle;
        this.registDate = registDate;
        this.register = register;
    }
}
