package com.pm.entity;

import com.pm.dto.ScheduleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * 
 * schedule Entity
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule")
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

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

    @Column(length = 100)
    private String title;

    @Column
    private Date scheduleDate;

    @Column
    private Time scheduleTime;

    @Column(length = 100)
    private String place;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(name = "register", insertable = false, updatable=false)
    private Long register;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register", referencedColumnName = "idx")
    private Member registerMember;

    @LastModifiedDate
    @Column(insertable = false)
    private Timestamp modifyDate;

    @Column
    private Long modifier;

    @Column
    private Character deleteYn;

    // DTO 변환
    public ScheduleDto toDto() {
        return ScheduleDto.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .actionItemIdx(actionItem == null ? null : actionItem.getIdx())
                .actionItemTitle(actionItem == null ? null : actionItem.getTitle())
                .testCaseIdx(testCase == null ? null : testCase.getIdx())
                .testCaseTitle(testCase == null ? null : testCase.getTitle())
                .title(title)
                .scheduleDate(scheduleDate)
                .scheduleTime(scheduleTime)
                .place(place)
                .registDate(registDate)
                .register(registerMember == null ? null : registerMember.getIdx())
                .registerName(registerMember == null ? null : registerMember.getName())
                .modifyDate(modifyDate)
                .modifier(modifier)
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public Schedule(Long idx, Long projectIdx, Long actionItemIdx, ActionItem actionItem, Long testCaseIdx, TestCase testCase, String title, Date scheduleDate, Time scheduleTime, String place, Timestamp registDate, Long register, Member registerMember, Timestamp modifyDate, Long modifier, Character deleteYn) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.actionItemIdx = actionItemIdx;
        this.actionItem = actionItem;
        this.testCaseIdx = testCaseIdx;
        this.testCase = testCase;
        this.title = title;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.place = place;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.registerMember = registerMember;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.deleteYn = deleteYn == null ? 'N' : deleteYn;
    }
}
