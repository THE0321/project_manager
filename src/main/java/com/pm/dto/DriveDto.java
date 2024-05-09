package com.pm.dto;

import com.pm.entity.Drive;
import com.pm.entity.File;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * drive DTO
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
@Setter
@ToString
@NoArgsConstructor
public class DriveDto {
    private Long idx;
    private Long projectIdx;
    private Long fileIdx;
    private String savePath;
    private String originName;
    private String extension;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public Drive toEntity() {
        File file = fileIdx == null ? null : File.builder().idx(fileIdx).build();

        return Drive.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .fileIdx(fileIdx)
                .file(file)
                .build();
    }

    @Builder
    public DriveDto(Long idx, Long projectIdx, Long fileIdx, String savePath, String originName, String extension, Timestamp registDate, Long register) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.fileIdx = fileIdx;
        this.savePath = savePath;
        this.originName = originName;
        this.extension = extension;
        this.registDate = registDate;
        this.register = register;
    }
}
