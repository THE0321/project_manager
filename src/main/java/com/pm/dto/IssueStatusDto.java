package com.pm.dto;

import com.pm.entity.IssueStatus;
import lombok.*;

/**
 *
 * issue_status DTO
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
public class IssueStatusDto {
    private Long idx;
    private String name;

    // Entity 변환
    public IssueStatus toEntity() {
        return IssueStatus.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public IssueStatusDto(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
