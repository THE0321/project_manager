package com.pm.dto;

import com.pm.entity.Project;
import com.pm.entity.ProjectStatus;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * project DTO
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
@Setter
@ToString
@NoArgsConstructor
public class ProjectDto {
    private Long idx;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long statusIdx;
    private String statusName;
    private Timestamp statusDate;
    private Timestamp registDate;
    private Long register;
    private Timestamp modifyDate;
    private Long modifier;

    // Entity 변환
    public Project toEntity() {
        ProjectStatus projectStatus = null;

        if(statusIdx != null) {
            projectStatus = ProjectStatus.builder().idx(statusIdx).build();
        }

        return Project.builder()
                .idx(idx)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .statusIdx(statusIdx)
                .projectStatus(projectStatus)
                .statusDate(statusDate)
                .registDate(registDate)
                .register(register)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .build();
    }

    @Builder
    public ProjectDto(Long idx, String title, String description, Date startDate, Date endDate, Long statusIdx, String statusName, Timestamp statusDate, Timestamp registDate, Long register, Timestamp modifyDate, Long modifier) {
        this.idx = idx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusIdx = statusIdx;
        this.statusName = statusName;
        this.statusDate = statusDate;
        this.registDate = registDate;
        this.register = register;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
    }
}
