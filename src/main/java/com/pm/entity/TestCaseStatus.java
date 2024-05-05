package com.pm.entity;

import com.pm.dto.TestCaseStatusDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * test_case_status Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test_case_status")
public class TestCaseStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30)
    private String name;

    // DTO 변환
    public TestCaseStatusDto toDto() {
        return TestCaseStatusDto.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public TestCaseStatus(Long idx, String name){
        this.idx = idx;
        this.name = name;
    }
}
