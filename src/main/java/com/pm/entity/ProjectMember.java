package com.pm.entity;

import com.pm.dto.ProjectMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 * 
 * project_manager Entity
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

    @Column
    private Long teamIdx;

    @Column
    private Long memberIdx;

    @Column
    private Character leaderYn;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(updatable = false)
    private Long register;

    // DTO 변환
    public ProjectMemberDto toDto() {
        return ProjectMemberDto.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .teamIdx(teamIdx)
                .memberIdx(memberIdx)
                .leaderYn(leaderYn)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ProjectMember(Long idx, Long projectIdx, Long teamIdx, Long memberIdx, Character leaderYn, Timestamp registDate, Long register) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.teamIdx = teamIdx;
        this.memberIdx = memberIdx;
        this.leaderYn = leaderYn;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
