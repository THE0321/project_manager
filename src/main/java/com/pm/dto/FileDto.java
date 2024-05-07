package com.pm.dto;

import com.pm.entity.File;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * file DTO
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long idx;
    private String savePath;
    private String originName;
    private String extension;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public File toEntity() {
        return File.builder()
                .idx(idx)
                .savePath(savePath)
                .originName(originName)
                .extension(extension)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public FileDto(Long idx, String savePath, String originName, String extension, Timestamp registDate, Long register) {
        this.idx = idx;
        this.savePath = savePath;
        this.originName = originName;
        this.extension = extension;
        this.registDate = registDate;
        this.register = register;
    }
}
