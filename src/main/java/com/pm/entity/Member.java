package com.pm.entity;

import com.pm.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

/**
 * 
 * member Entity
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
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "position_idx", insertable = false, updatable=false)
    private Long positionIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_idx", referencedColumnName = "idx")
    private Position position;

    @Column(name = "role_idx", insertable = false, updatable=false)
    private Long roleIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_idx", referencedColumnName = "idx")
    private Role role;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String account;

    @Column(length = 300)
    private String password;

    @Column(length = 500)
    private String note;

    @Column
    private Character disableYn;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column(name = "register", insertable = false, updatable=false)
    private Long register;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register", referencedColumnName = "idx")
    private Member registerMember;

    @LastModifiedDate
    @Column(insertable = false)
    private Timestamp modifyDate;

    @Column(name = "modifier", insertable = false, updatable=false)
    private Long modifier;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier", referencedColumnName = "idx")
    private Member modifierMember;

    @Column
    private Character deleteYn;

    // DTO 변환
    public MemberDto toDto() {
        return MemberDto.builder()
                .idx(idx)
                .positionIdx(position == null ? null : position.getIdx())
                .positionName(position == null ? null : position.getName())
                .roleIdx(role == null ? null : role.getIdx())
                .roleName(role == null ? null : role.getName())
                .name(name)
                .account(account)
                .password(password)
                .note(note)
                .disableYn(disableYn)
                .registDate(registDate)
                .register(registerMember == null ? null : registerMember.getIdx())
                .registerName(registerMember == null ? null : registerMember.getName())
                .modifyDate(modifyDate)
                .modifier(modifierMember == null ? null : modifierMember.getIdx())
                .modifierName(modifierMember == null ? null : modifierMember.getName())
                .deleteYn(deleteYn)
                .build();
    }

    @Builder
    public Member(Long idx, Long positionIdx, Position position, Long roleIdx, Role role, String name, String account, String password, String note, Character disableYn, Timestamp registDate, Long register, Member registerMember, Timestamp modifyDate, Long modifier, Member modifierMember, Character deleteYn) {
        this.idx = idx;
        this.positionIdx = positionIdx;
        this.position = position;
        this.roleIdx = roleIdx;
        this.role =  role;
        this.name = name;
        this.account = account;
        this.password = password;
        this.note = note;
        this.disableYn = disableYn == null ? 'N' : disableYn;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
        this.registerMember = registerMember;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierMember = modifierMember;
        this.deleteYn = deleteYn == null ? 'Y' : deleteYn;
    }
}
