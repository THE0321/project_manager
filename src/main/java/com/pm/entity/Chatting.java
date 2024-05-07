package com.pm.entity;

import com.pm.dto.ChattingDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 * 
 * chatting Entity
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
@Table(name = "chatting")
public class Chatting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long chattingRoomIdx;

    @Column
    private Long fileIdx;

    @Column(length = 300)
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(name = "register", insertable = false, updatable=false)
    private Long register;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register", referencedColumnName = "idx")
    private Member member;

    // DTO 변환
    public ChattingDto toDto() {
        return ChattingDto.builder()
                .idx(idx)
                .chattingRoomIdx(chattingRoomIdx)
                .fileIdx(fileIdx)
                .message(message)
                .registDate(registDate)
                .register(member == null ? null : member.getIdx())
                .registerProfileIdx(member == null ? null : member.getProfileIdx())
                .registerName(member == null ? null : member.getName())
                .build();
    }

    @Builder
    public Chatting(Long idx, Long chattingRoomIdx, Long fileIdx, String message, Timestamp registDate, Long register, Member member) {
        this.idx = idx;
        this.chattingRoomIdx = chattingRoomIdx;
        this.fileIdx = fileIdx;
        this.message = message;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.member = member;
    }
}
