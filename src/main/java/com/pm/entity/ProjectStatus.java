package com.pm.entity;

import com.pm.dto.ProjectStatusDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * project_status Entity
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
public class ProjectStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30)
    private String name;

    // DTO 변환
    public ProjectStatusDto toDto() {
        return ProjectStatusDto.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public ProjectStatus(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
