package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.ProjectMember;
import com.pm.entity.Team;
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
    private String teamName;
    private Long memberIdx;
    private Long memberProfileIdx;
    private String memberName;
    private Character leaderYn;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public ProjectMember toEntity() {
        Team team = teamIdx == null ? null : Team.builder().idx(teamIdx).build();
        Member member = memberIdx == null ? null : Member.builder().idx(memberIdx).build();

        return ProjectMember.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .teamIdx(teamIdx)
                .team(team)
                .memberIdx(memberIdx)
                .member(member)
                .leaderYn(leaderYn)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ProjectMemberDto(Long idx, Long projectIdx, Long teamIdx, String teamName, Long memberIdx, Long memberProfileIdx, String memberName, Character leaderYn, Timestamp registDate, Long register) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.memberIdx = memberIdx;
        this.memberProfileIdx = memberProfileIdx;
        this.memberName = memberName;
        this.leaderYn = leaderYn;
        this.registDate = registDate;
        this.register = register;
    }
}
