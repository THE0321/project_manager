package com.pm.dto;

import com.pm.entity.ChattingMember;
import com.pm.entity.ChattingRoom;
import com.pm.entity.Member;
import lombok.*;

import java.sql.Timestamp;

/**
 * 
 * chatting_member DTO
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
public class ChattingMemberDto {
    private Long idx;
    private Long memberIdx;
    private Long memberProfileIdx;
    private String memberName;
    private Long chattingRoomIdx;
    private String chattingRoomTitle;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public ChattingMember toEntity() {
        Member member = memberIdx == null ? null : Member.builder().idx(memberIdx).build();
        ChattingRoom chattingRoom = chattingRoomIdx == null ? null : ChattingRoom.builder().idx(chattingRoomIdx).build();

        return ChattingMember.builder()
                .idx(idx)
                .memberIdx(memberIdx)
                .member(member)
                .chattingRoomIdx(chattingRoomIdx)
                .chattingRoom(chattingRoom)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ChattingMemberDto(Long idx, Long memberIdx, Long memberProfileIdx, String memberName, Long chattingRoomIdx, String chattingRoomTitle, Timestamp registDate, Long register) {
        this.idx = idx;
        this.memberIdx = memberIdx;
        this.memberProfileIdx = memberProfileIdx;
        this.memberName = memberName;
        this.chattingRoomIdx = chattingRoomIdx;
        this.chattingRoomTitle = chattingRoomTitle;
        this.registDate = registDate;
        this.register = register;
    }
}
