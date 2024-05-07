package com.pm.entity;

import com.pm.dto.FileDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * file Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 300)
    private String savePath;

    @Column(length = 300)
    private String originName;

    @Column(length = 20)
    private String extension;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // DTO 변환
    public FileDto toDto() {
        return FileDto.builder()
                .idx(idx)
                .savePath(savePath)
                .originName(originName)
                .extension(extension)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public File(Long idx, String savePath, String originName, String extension, Timestamp registDate, Long register) {
        this.idx = idx;
        this.savePath = savePath;
        this.originName = originName;
        this.extension = extension;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
