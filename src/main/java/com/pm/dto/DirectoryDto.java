package com.pm.dto;

import com.pm.entity.Directory;
import com.pm.entity.DirectoryStatus;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Timestamp;

/**
 * 
 * directory DTO
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
public class DirectoryDto {
    private Long idx;
    private Long projectIdx;
    private String title;
    private String description;
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
    public Directory toEntity() {
        DirectoryStatus directoryStatus = statusIdx == null ? null : DirectoryStatus.builder().idx(statusIdx).build();
        Member registerMember = register == null ? null : Member.builder().idx(register).build();
        Member modifierMember = modifier == null ? null : Member.builder().idx(modifier).build();

        return Directory.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .title(title)
                .description(description)
                .statusIdx(statusIdx)
                .directoryStatus(directoryStatus)
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
    public DirectoryDto(Long idx, Long projectIdx, String title, String description, Long statusIdx, String statusName, Timestamp statusDate, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName, Character deleteYn) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.title = title;
        this.description = description;
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
