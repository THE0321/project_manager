package com.pm.dto;

import com.pm.entity.ActionItem;
import com.pm.entity.TestCase;
import com.pm.entity.TestCaseMapping;
import lombok.*;

import java.sql.Timestamp;

/**
 * 
 * test_case_mapping DTO
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestCaseMappingDto {
    private Long idx;
    private Long actionItemIdx;
    private String actionItemTitle;
    private Long testCaseIdx;
    private String testCaseTitle;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public TestCaseMapping toEntity() {
        ActionItem actionItem = actionItemIdx == null ? null : ActionItem.builder().idx(actionItemIdx).build();
        TestCase testCase = testCaseIdx == null ? null : TestCase.builder().idx(testCaseIdx).build();

        return TestCaseMapping.builder()
                .idx(idx)
                .actionItemIdx(actionItemIdx)
                .actionItem(actionItem)
                .testCaseIdx(testCaseIdx)
                .testCase(testCase)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public TestCaseMappingDto(Long idx, Long actionItemIdx, String actionItemTitle, Long testCaseIdx, String testCaseTitle, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItemTitle = actionItemTitle;
        this.testCaseIdx = testCaseIdx;
        this.testCaseTitle = testCaseTitle;
        this.registDate = registDate;
        this.register = register;
    }
}
