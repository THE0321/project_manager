package com.pm.entity;

import com.pm.dto.TestCaseMappingDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * test_case_mapping Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test_case_mapping")
public class TestCaseMapping {
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

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // DTO 변환
    public TestCaseMappingDto toDto() {
        return TestCaseMappingDto.builder()
                .idx(idx)
                .actionItemIdx(actionItem == null ? null : actionItem.getIdx())
                .actionItemTitle(actionItem == null ? null : actionItem.getTitle())
                .testCaseIdx(testCase == null ? null : testCase.getIdx())
                .testCaseTitle(testCase == null ? null : testCase.getTitle())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public TestCaseMapping(Long idx, Long actionItemIdx, ActionItem actionItem, Long testCaseIdx, TestCase testCase, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItem = actionItem;
        this.testCaseIdx = testCaseIdx;
        this.testCase = testCase;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
