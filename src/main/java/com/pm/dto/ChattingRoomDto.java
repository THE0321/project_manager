package com.pm.dto;

import com.pm.entity.ChattingRoom;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Timestamp;

/**
 * 
 * chatting_room DTO
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
public class ChattingRoomDto {
    private Long idx;
    private String code;
    private String title;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private String modifierName;
    private Character deleteYn;

    // Entity 변환
    public ChattingRoom toEntity() {
        Member registerMember = register == null ? null : Member.builder().idx(register).build();
        Member modifierMember = modifyDate == null ? null : Member.builder().idx(modifier).build();

        return ChattingRoom.builder()
                .idx(idx)
                .code(code)
                .title(title)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .modifierMember(modifierMember)
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public ChattingRoomDto(Long idx, String code, String title, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName, Character deleteYn) {
        this.idx = idx;
        this.code = code;
        this.title = title;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierName = modifierName;
        this.deleteYn = deleteYn;
    }
}
