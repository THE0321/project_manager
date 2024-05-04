package com.pm.entity;

import com.pm.dto.DirectoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

/**
 * 
 * directory Entity
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
@Table(name = "directory")
public class Directory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(name = "status_idx", insertable = false, updatable=false)
    private Long statusIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_idx", referencedColumnName = "idx")
    private DirectoryStatus directoryStatus;

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

    @Column
    private Character deleteYn;

    // DTO 변환
    public DirectoryDto toDto() {
        return DirectoryDto.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .title(title)
                .description(description)
                .statusIdx(directoryStatus == null ? null : directoryStatus.getIdx())
                .statusName(directoryStatus == null ? null : directoryStatus.getName())
                .statusDate(statusDate)
                .registDate(registDate)
                .register(registerMember == null ? null : registerMember.getIdx())
                .registerName(registerMember == null ? null : registerMember.getName())
                .modifyDate(modifyDate)
                .modifier(modifierMember == null ? null : modifierMember.getIdx())
                .modifierName(modifierMember == null ? null : modifierMember.getName())
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public Directory(Long idx, Long projectIdx, String title, String description, Long statusIdx, DirectoryStatus directoryStatus, Timestamp statusDate, Timestamp registDate, Long register, Member registerMember, Timestamp modifyDate, Long modifier, Member modifierMember, Character deleteYn) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.title = title;
        this.description = description;
        this.statusIdx = statusIdx;
        this.directoryStatus = directoryStatus;
        this.statusDate = statusDate;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.registerMember = registerMember;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierMember = modifierMember;
        this.deleteYn = deleteYn == null ? 'N' : deleteYn;
    }
}
