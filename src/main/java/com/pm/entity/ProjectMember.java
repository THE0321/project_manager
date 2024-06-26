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
@Table(name = "project_member")
public class ProjectMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

    @Column(name = "team_idx", insertable = false, updatable=false)
    private Long teamIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_idx", referencedColumnName = "idx")
    private Team team;

    @Column(name = "member_idx", insertable = false, updatable=false)
    private Long memberIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "idx")
    private Member member;

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
                .teamIdx(team == null ? null : team.getIdx())
                .teamName(team == null ? null : team.getName())
                .memberIdx(member == null ? null : member.getIdx())
                .memberProfileIdx(member == null ? null : member.getProfileIdx())
                .memberName(member == null ? null : member.getName())
                .leaderYn(leaderYn)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ProjectMember(Long idx, Long projectIdx, Long teamIdx, Team team, Long memberIdx, Member member, Character leaderYn, Timestamp registDate, Long register) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.teamIdx = teamIdx;
        this.team = team;
        this.memberIdx = memberIdx;
        this.member = member;
        this.leaderYn = leaderYn;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
