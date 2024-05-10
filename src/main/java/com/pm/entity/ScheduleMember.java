package com.pm.entity;

import com.pm.dto.ScheduleMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 * 
 * schedule_member Entity
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
@Table(name = "schedule_member")
public class ScheduleMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "schedule_idx", insertable = false, updatable=false)
    private Long scheduleIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_idx", referencedColumnName = "idx")
    private Schedule schedule;

    @Column(name = "member_idx", insertable = false, updatable=false)
    private Long memberIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "idx")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // Entity 변환
    public ScheduleMemberDto toEntity() {
        return ScheduleMemberDto.builder()
                .idx(idx)
                .scheduleIdx(schedule == null ? null : schedule.getIdx())
                .scheduleTitle(schedule == null ? null : schedule.getTitle())
                .scheduleDate(schedule == null ? null : schedule.getScheduleDate())
                .scheduleTime(schedule == null ? null : schedule.getScheduleTime())
                .scheduleRegistDate(schedule == null ? null : schedule.getRegistDate())
                .memberIdx(member == null ? null : member.getIdx())
                .memberProfileIdx(member == null ? null : member.getProfileIdx())
                .memberName(member == null ? null : member.getName())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ScheduleMember(Long idx, Long scheduleIdx, Schedule schedule, Long memberIdx, Member member, Timestamp registDate, Long register) {
        this.idx = idx;
        this.scheduleIdx = scheduleIdx;
        this.schedule = schedule;
        this.memberIdx = memberIdx;
        this.member = member;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
