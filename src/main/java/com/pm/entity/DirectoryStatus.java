package com.pm.entity;

import com.pm.dto.DirectoryStatusDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * directory_status Entity
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
@Table(name = "directory_status")
public class DirectoryStatus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30)
    private String name;

    // DTO 변환
    public DirectoryStatusDto toDto() {
        return DirectoryStatusDto.builder()
                .idx(idx)
                .name(name)
                .build();
    }

    @Builder
    public DirectoryStatus(Long idx, String name) {
        this.idx = idx;
        this.name = name;
    }
}
