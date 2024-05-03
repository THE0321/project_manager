package com.pm.entity;

import com.pm.dto.PositionDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 * 
 * position Entity
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "position")
public class Position {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 30)
    private String name;

    @Column
    private Integer orderNumber;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(name = "register", insertable = false, updatable=false)
    private Long register;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register", referencedColumnName = "idx")
    private Member registerMember;

    // DTO 변환
    public PositionDto toDto() {
        return PositionDto.builder()
                .idx(idx)
                .name(name)
                .registDate(registDate)
                .register(registerMember == null ? null : registerMember.getIdx())
                .registerName(registerMember == null ? null : registerMember.getName())
                .build();
    }

    @Builder
    public Position(Long idx, String name, Integer orderNumber, Timestamp registDate, Long register, Member registerMember) {
        this.idx = idx;
        this.name = name;
        this.orderNumber = orderNumber;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.registerMember = registerMember;
    }
}
