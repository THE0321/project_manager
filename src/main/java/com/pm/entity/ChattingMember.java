package com.pm.entity;

import com.pm.dto.ChattingMemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 *
 * chatting_member Entity
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
@Table(name = "chatting_member")
public class ChattingMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "member_idx", insertable = false, updatable=false)
    private Long memberIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "idx")
    private Member member;

    @Column(name = "chatting_room_idx", insertable = false, updatable=false)
    private Long chattingRoomIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_room_idx", referencedColumnName = "idx")
    private ChattingRoom chattingRoom;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    // DTO 변환
    public ChattingMemberDto toDto() {
        return ChattingMemberDto.builder()
                .idx(idx)
                .memberIdx(member == null ? null : member.getIdx())
                .memberProfileIdx(member == null ? null : member.getProfileIdx())
                .memberName(member == null ? null : member.getName())
                .chattingRoomIdx(chattingRoom == null ? null : chattingRoom.getIdx())
                .chattingRoomTitle(chattingRoom == null ? null : chattingRoom.getTitle())
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public ChattingMember(Long idx, Long memberIdx, Member member, Long chattingRoomIdx, ChattingRoom chattingRoom, Timestamp registDate, Long register) {
        this.idx = idx;
        this.memberIdx = memberIdx;
        this.member = member;
        this.chattingRoomIdx = chattingRoomIdx;
        this.chattingRoom = chattingRoom;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
