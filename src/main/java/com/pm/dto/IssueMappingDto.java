package com.pm.dto;

import com.pm.entity.ActionItem;
import com.pm.entity.Issue;
import com.pm.entity.IssueMapping;
import com.pm.entity.TestCase;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * issue_mapping DTO
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
public class IssueMappingDto {
    private Long idx;
    private Long actionItemIdx;
    private String actionItemTitle;
    private Long testCaseIdx;
    private String testCaseTitle;
    private Long issueIdx;
    private String issueTitle;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public IssueMapping toEntity() {
        ActionItem actionItem = actionItemIdx == null ? null : ActionItem.builder().idx(actionItemIdx).build();
        TestCase testCase = testCaseIdx == null ? null : TestCase.builder().idx(testCaseIdx).build();
        Issue issue = issueIdx == null ? null : Issue.builder().idx(issueIdx).build();

        return IssueMapping.builder()
                .idx(idx)
                .actionItemIdx(actionItemIdx)
                .actionItem(actionItem)
                .testCaseIdx(testCaseIdx)
                .testCase(testCase)
                .issueIdx(issueIdx)
                .issue(issue)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public IssueMappingDto(Long idx, Long actionItemIdx, String actionItemTitle, Long testCaseIdx, String testCaseTitle, Long issueIdx, String issueTitle, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItemTitle = actionItemTitle;
        this.testCaseIdx = testCaseIdx;
        this.testCaseTitle = testCaseTitle;
        this.issueIdx = issueIdx;
        this.issueTitle = issueTitle;
        this.registDate = registDate;
        this.register = register;
    }
}
