package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.Team;
import com.pm.entity.TeamMember;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * team_member DTO
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-03
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-03          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeamMemberDto {
    private Long idx;
    private Long teamIdx;
    private String teamName;
    private Long memberIdx;
    private String memberName;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public TeamMember toEntity() {
        Team team = teamIdx == null ? null : Team.builder().idx(teamIdx).build();
        Member member = memberIdx == null ? null : Member.builder().idx(memberIdx).build();

        return TeamMember.builder()
                .idx(idx)
                .teamIdx(teamIdx)
                .team(team)
                .memberIdx(memberIdx)
                .member(member)
                .registDate(registDate)
                .register(register)
                .build();
    }


    @Builder
    public TeamMemberDto(Long idx, Long teamIdx, String teamName, Long memberIdx, String memberName, Timestamp registDate, Long register) {
        this.idx = idx;
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.memberIdx = memberIdx;
        this.memberName = memberName;
        this.registDate = registDate;
        this.register = register;
    }
}
