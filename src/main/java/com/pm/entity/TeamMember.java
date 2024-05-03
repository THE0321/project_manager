package com.pm.entity;

import com.pm.dto.TeamMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * team_member Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "team_member")
public class TeamMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

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

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // DTO 변환
    public TeamMemberDto toDto() {
        return TeamMemberDto.builder()
                .idx(idx)
                .teamIdx(team == null ? null : team.getIdx())
                .teamName(team == null ? null : team.getName())
                .memberIdx(member == null ? null : member.getIdx())
                .memberName(member == null ? null : member.getName())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public TeamMember(Long idx, Long teamIdx, Team team, Long memberIdx, Member member, Timestamp registDate, Long register) {
        this.idx = idx;
        this.teamIdx = teamIdx;
        this.team = team;
        this.memberIdx = memberIdx;
        this.member = member;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
