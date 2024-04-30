package com.pm.entity;

import com.pm.dto.ProjectDto;
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
 * project Entity
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
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

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
    private ProjectStatus projectStatus;

    @Column
    private Timestamp statusDate;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(updatable = false)
    private Long register;

    @LastModifiedDate
    @Column(insertable = false)
    private Timestamp modifyDate;

    @Column(insertable = false)
    private Long modifier;

    // DTO 변환
    public ProjectDto toDto() {
        return ProjectDto.builder()
                .idx(idx)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .statusIdx(statusIdx)
                .statusName(projectStatus == null ? null : projectStatus.getName())
                .statusDate(statusDate)
                .registDate(registDate)
                .register(register)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .build();
    }

    @Builder
    public Project(Long idx, String title, String description, Date startDate, Date endDate, Long statusIdx, ProjectStatus projectStatus, Timestamp statusDate, Timestamp registDate, Long register, Timestamp modifyDate, Long modifier) {
        this.idx = idx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusIdx = statusIdx;
        this.projectStatus = projectStatus;
        this.statusDate = statusDate;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
    }
}
