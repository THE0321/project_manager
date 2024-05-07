package com.pm.entity;

import com.pm.dto.IssueMappingDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * issue_mapping Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "issue_mapping")
public class IssueMapping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "action_item_idx", insertable = false, updatable=false)
    private Long actionItemIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_item_idx", referencedColumnName = "idx")
    private ActionItem actionItem;

    @Column(name = "test_case_idx", insertable = false, updatable=false)
    private Long testCaseIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_idx", referencedColumnName = "idx")
    private TestCase testCase;

    @Column(name = "issue_idx", insertable = false, updatable=false)
    private Long issueIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_idx", referencedColumnName = "idx")
    private Issue issue;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // DTO 변환
    public IssueMappingDto toDto() {
        return IssueMappingDto.builder()
                .idx(idx)
                .actionItemIdx(actionItem == null ? null : actionItem.getIdx())
                .actionItemTitle(actionItem == null ? null : actionItem.getTitle())
                .testCaseIdx(testCase == null ? null : testCase.getIdx())
                .testCaseTitle(testCase == null ? null : testCase.getTitle())
                .issueIdx(issue == null ? null : issue.getIdx())
                .issueTitle(issue == null ? null : issue.getTitle())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public IssueMapping(Long idx, Long actionItemIdx, ActionItem actionItem, Long testCaseIdx, TestCase testCase, Long issueIdx, Issue issue, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItem = actionItem;
        this.testCaseIdx = testCaseIdx;
        this.testCase = testCase;
        this.issueIdx = issueIdx;
        this.issue = issue;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
