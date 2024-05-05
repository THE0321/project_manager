package com.pm.entity;

import com.pm.dto.ActionItemMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * action_item_member JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "action_item_member")
public class ActionItemMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "action_item_idx", insertable = false, updatable=false)
    private Long actionItemIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_item_idx", referencedColumnName = "idx")
    private ActionItem actionItem;

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
    public ActionItemMemberDto toDto() {
        return ActionItemMemberDto.builder()
                .idx(idx)
                .actionItemIdx(actionItem == null ? null : actionItem.getIdx())
                .actionItemTitle(actionItem == null ? null : actionItem.getTitle())
                .memberIdx(member == null ? null : member.getIdx())
                .memberProfileIdx(member == null ? null : member.getProfileIdx())
                .memberName(member == null ? null : member.getName())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ActionItemMember(Long idx, Long actionItemIdx, ActionItem actionItem, Long memberIdx, Member member, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItem = actionItem;
        this.memberIdx = memberIdx;
        this.member = member;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
