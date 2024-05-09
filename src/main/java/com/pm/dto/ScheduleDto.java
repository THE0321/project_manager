package com.pm.dto;

import com.pm.entity.ActionItem;
import com.pm.entity.Member;
import com.pm.entity.Schedule;
import com.pm.entity.TestCase;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * schedule DTO
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
@Setter
@ToString
@NoArgsConstructor
public class ScheduleDto {
    private Long idx;
    private Long projectIdx;
    private Long actionItemIdx;
    private String actionItemTitle;
    private Long testCaseIdx;
    private String testCaseTitle;
    private String title;
    private Date scheduleDate;
    private Time scheduleTime;
    private String place;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private Character deleteYn;

    // Entity 변환
    public Schedule toEntity() {
        ActionItem actionItem = actionItemIdx == null ? null : ActionItem.builder().idx(actionItemIdx).build();
        TestCase testCase = testCaseIdx == null ? null : TestCase.builder().idx(testCaseIdx).build();
        Member registerMember = register == null ? null : Member.builder().idx(register).build();

        return Schedule.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .actionItemIdx(actionItemIdx)
                .actionItem(actionItem)
                .testCaseIdx(testCaseIdx)
                .testCase(testCase)
                .title(title)
                .scheduleDate(scheduleDate)
                .scheduleTime(scheduleTime)
                .place(place)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public ScheduleDto(Long idx, Long projectIdx, Long actionItemIdx, String actionItemTitle, Long testCaseIdx, String testCaseTitle, String title, Date scheduleDate, Time scheduleTime, String place, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, Character deleteYn) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.actionItemIdx = actionItemIdx;
        this.actionItemTitle = actionItemTitle;
        this.testCaseIdx = testCaseIdx;
        this.testCaseTitle = testCaseTitle;
        this.title = title;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.place = place;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.deleteYn = deleteYn;
    }
}
