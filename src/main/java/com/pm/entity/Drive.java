package com.pm.entity;

import com.pm.dto.DriveDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * drive Entity
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "drive")
public class Drive {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long projectIdx;

    @Column(name = "file_idx", insertable = false, updatable=false)
    private Long fileIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_idx", referencedColumnName = "idx")
    private File file;

    // DTO 변환
    public DriveDto toDto() {
        return DriveDto.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .fileIdx(file == null ? null : file.getIdx())
                .savePath(file == null ? null : file.getSavePath())
                .originName(file == null ? null : file.getOriginName())
                .extension(file == null ? null : file.getExtension())
                .register(file == null ? null : file.getRegister())
                .registDate(file == null ? null : file.getRegistDate())
                .build();
    }

    @Builder
    public Drive(Long idx, Long projectIdx, Long fileIdx, File file) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.fileIdx = fileIdx;
        this.file = file;
    }
}
