package com.pm.dto;

import com.pm.entity.Schedule;
import com.pm.entity.ScheduleMember;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * 
 * schedule_member DTO
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
public class ScheduleMemberDto {
    private Long idx;
    private Long scheduleIdx;
    private String scheduleTitle;
    private Date scheduleDate;
    private Time scheduleTime;
    private Timestamp scheduleRegistDate;
    private Long memberIdx;
    private Timestamp registDate;
    private Long register;

    // DTO 변환
    public ScheduleMember toEntity() {
        Schedule schedule = scheduleIdx == null ? null : Schedule.builder().idx(scheduleIdx).build();

        return ScheduleMember.builder()
                .idx(idx)
                .scheduleIdx(scheduleIdx)
                .schedule(schedule)
                .memberIdx(memberIdx)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ScheduleMemberDto(Long idx, Long scheduleIdx, String scheduleTitle, Date scheduleDate, Time scheduleTime, Timestamp scheduleRegistDate, Long memberIdx, Timestamp registDate, Long register) {
        this.idx = idx;
        this.scheduleIdx = scheduleIdx;
        this.scheduleTitle = scheduleTitle;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.scheduleRegistDate = scheduleRegistDate;
        this.memberIdx = memberIdx;
        this.registDate = registDate;
        this.register = register;
    }
}
