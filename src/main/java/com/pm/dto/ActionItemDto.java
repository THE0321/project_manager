package com.pm.dto;

import com.pm.entity.ActionItem;
import com.pm.entity.ActionItemStatus;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Date;
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
public class ActionItemDto {
    private Long idx;
    private Long directoryIdx;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long statusIdx;
    private String statusName;
    private Timestamp statusDate;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private String modifierName;
    private Character deleteYn;

    // Entity 변환
    public ActionItem toEntity() {
        ActionItemStatus actionItemStatus = statusIdx == null ? null : ActionItemStatus.builder().idx(statusIdx).build();
        Member registerMember = register == null ? null : Member.builder().idx(register).build();
        Member modifierMember = modifier == null ? null : Member.builder().idx(modifier).build();

        return ActionItem.builder()
                .idx(idx)
                .directoryIdx(directoryIdx)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .statusIdx(statusIdx)
                .actionItemStatus(actionItemStatus)
                .statusDate(statusDate)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .modifierMember(modifierMember)
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public ActionItemDto(Long idx, Long directoryIdx, String title, String description, Date startDate, Date endDate, Long statusIdx, String statusName, Timestamp statusDate, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName, Character deleteYn) {
        this.idx = idx;
        this.directoryIdx = directoryIdx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusIdx = statusIdx;
        this.statusName = statusName;
        this.statusDate = statusDate;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierName = modifierName;
        this.deleteYn = deleteYn;
    }
}
