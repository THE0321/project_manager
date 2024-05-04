package com.pm.dto;

import com.pm.entity.DirectoryStatus;
import lombok.*;

/**
 * 
 * directory_status DTO
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
public class DirectoryStatusDto {
    private Long idx;
    private String name;

    // Entity 변환
    public DirectoryStatus toEntity() {
        return DirectoryStatus.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public DirectoryStatusDto(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
