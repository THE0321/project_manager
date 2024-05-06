package com.pm.entity;

import com.pm.dto.IssueStatusDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * issue_status Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "issue_status")
public class IssueStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30)
    private String name;

    // DTO 변환
    public IssueStatusDto toDto() {
        return IssueStatusDto.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public IssueStatus(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
