package com.pm.dto;

import lombok.*;

/**
 * 
 * project_status DTO
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
public class ProjectStatusDto {
    private Long idx;
    private String name;

    @Builder
    public ProjectStatusDto(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
