package com.pm.dto;

import com.pm.entity.ActionItem;
import com.pm.entity.ActionItemMember;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * action_item DTO
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
@Setter
@ToString
@NoArgsConstructor
public class ActionItemMemberDto {
    private Long idx;
    private Long actionItemIdx;
    private String actionItemTitle;
    private Long memberIdx;
    private String memberName;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public ActionItemMember toEntity() {
        ActionItem actionItem = actionItemIdx == null ? null : ActionItem.builder().idx(actionItemIdx).build();
        Member member = memberIdx ==  null ? null : Member.builder().idx(memberIdx).build();

        return ActionItemMember.builder()
                .idx(idx)
                .actionItemIdx(actionItemIdx)
                .actionItem(actionItem)
                .memberIdx(memberIdx)
                .member(member)
                .registDate(registDate)
                .register(memberIdx)
                .build();
    }

    @Builder
    public ActionItemMemberDto(Long idx, Long actionItemIdx, String actionItemTitle, Long memberIdx, String memberName, Timestamp registDate, Long register) {
        this.idx = idx;
        this.actionItemIdx = actionItemIdx;
        this.actionItemTitle = actionItemTitle;
        this.memberIdx = memberIdx;
        this.memberName = memberName;
        this.registDate = registDate;
        this.register = register;
    }
}
