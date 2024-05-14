package com.pm.dto;

import com.pm.entity.Chatting;
import com.pm.entity.File;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * chatting DTO
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
public class ChattingDto {
    private Long idx;
    private Long chattingRoomIdx;
    private Long fileIdx;
    private String fileExtension;
    private String fileName;
    private String message;
    private Timestamp registDate;
    private Long register;
    private Long registerProfileIdx;
    private String registerName;

    // Entity 변환
    public Chatting toEntity() {
        File file = fileIdx == null ? null : File.builder().idx(fileIdx).build();
        Member member = register == null ? null : Member.builder().idx(register).build();

        return Chatting.builder()
                .idx(idx)
                .chattingRoomIdx(chattingRoomIdx)
                .fileIdx(fileIdx)
                .file(file)
                .message(message)
                .registDate(registDate)
                .register(register)
                .member(member)
                .build();
    }

    @Builder
    public ChattingDto(Long idx, Long chattingRoomIdx, Long fileIdx, String fileExtension, String fileName, String message, Timestamp registDate, Long register, Long registerProfileIdx, String registerName) {
        this.idx = idx;
        this.chattingRoomIdx = chattingRoomIdx;
        this.fileIdx = fileIdx;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.message = message;
        this.registDate = registDate;
        this.register = register;
        this.registerProfileIdx = registerProfileIdx;
        this.registerName = registerName;
    }
}
