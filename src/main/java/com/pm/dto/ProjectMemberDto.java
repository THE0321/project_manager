package com.pm.dto;

import com.pm.entity.ProjectMember;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * project_member DTO
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
@Setter
@ToString
@NoArgsConstructor
public class ProjectMemberDto {
    private Long idx;
    private Long projectIdx;
    private Long teamIdx;
    private Long memberIdx;
    private Character leaderYn;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public ProjectMember toEntity() {
        return ProjectMember.builder()
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
    public ProjectMemberDto(Long idx, Long projectIdx, Long teamIdx, Long memberIdx, Character leaderYn, Timestamp registDate, Long register) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.teamIdx = teamIdx;
        this.memberIdx = memberIdx;
        this.leaderYn = leaderYn;
        this.registDate = registDate;
        this.register = register;
    }
}
