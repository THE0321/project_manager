package com.pm.dto;

import com.pm.entity.TestCaseStatus;
import lombok.*;

/**
 * 
 * test_case_status DTO
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestCaseStatusDto {
    private Long idx;
    private String name;

    // Entity 변환
    public TestCaseStatus toEntity() {
        return TestCaseStatus.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public TestCaseStatusDto(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
