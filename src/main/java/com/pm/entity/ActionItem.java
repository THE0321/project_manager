package com.pm.entity;

import com.pm.dto.ActionItemDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * action_item Entity
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
@Table(name = "action_item")
public class ActionItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

    @Column
    private Long directoryIdx;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column(name = "status_idx", insertable = false, updatable=false)
    private Long statusIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_idx", referencedColumnName = "idx")
    private ActionItemStatus actionItemStatus;

    @Column
    private Timestamp statusDate;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(name = "register", insertable = false, updatable=false)
    private Long register;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register", referencedColumnName = "idx")
    private Member registerMember;

    @LastModifiedDate
    @Column(insertable = false)
    private Timestamp modifyDate;

    @Column(name = "modifier", insertable = false, updatable=false)
    private Long modifier;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier", referencedColumnName = "idx")
    private Member modifierMember;

    // DTO 변환
    public ActionItemDto toDto() {
        return ActionItemDto.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .directoryIdx(directoryIdx)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .statusIdx(actionItemStatus == null ? null : actionItemStatus.getIdx())
                .statusName(actionItemStatus == null ? null : actionItemStatus.getName())
                .registDate(registDate)
                .register(registerMember == null ? null : registerMember.getIdx())
                .registerName(registerMember == null ? null : registerMember.getName())
                .modifyDate(modifyDate)
                .modifier(modifierMember == null ? null : modifierMember.getIdx())
                .modifierName(modifierMember == null ? null : modifierMember.getName())
                .build();
    }

    @Builder
    public ActionItem(Long idx, Long projectIdx, Long directoryIdx, String title, String description, Date startDate, Date endDate, Long statusIdx, ActionItemStatus actionItemStatus, Timestamp statusDate, Timestamp registDate, Long register, Member registerMember, Timestamp modifyDate, Long modifier, Member modifierMember) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.directoryIdx = directoryIdx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusIdx = statusIdx;
        this.actionItemStatus = actionItemStatus;
        this.statusDate = statusDate;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.registerMember = registerMember;
        this.modifier = modifier;
        this.modifyDate = modifyDate;
        this.modifierMember = modifierMember;
    }
}
