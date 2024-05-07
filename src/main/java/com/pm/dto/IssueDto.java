package com.pm.dto;

import com.pm.entity.Issue;
import com.pm.entity.IssueStatus;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * issue DTO
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-06
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-06          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class IssueDto {
    private Long idx;
    private Long projectIdx;
    private String title;
    private String description;
    private Long statusIdx;
    private String statusName;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private String modifierName;

    // Entity 변환
    public Issue toEntity() {
        IssueStatus issueStatus = statusIdx == null ? null : IssueStatus.builder().idx(statusIdx).build();
        Member registerMember = register == null ? null : Member.builder().idx(register).build();
        Member modifierMember = modifier == null ? null : Member.builder().idx(modifier).build();

        return Issue.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .title(title)
                .description(description)
                .statusIdx(statusIdx)
                .issueStatus(issueStatus)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .modifierMember(modifierMember)
                .build();
    }

    @Builder
    public IssueDto(Long idx, Long projectIdx, String title, String description, Long statusIdx, String statusName, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.title = title;
        this.description = description;
        this.statusIdx = statusIdx;
        this.statusName = statusName;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierName = modifierName;
    }
}
