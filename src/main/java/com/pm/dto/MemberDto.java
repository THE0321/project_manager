package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.Position;
import com.pm.entity.Role;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * member DTO
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
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long idx;
    private Long profileIdx;
    private String positionName;
    private Long positionIdx;
    private String roleName;
    private Long roleIdx;
    private String name;
    private String account;
    private String password;
    private String note;
    private Character disableYn;
    private Character adminYn;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private String modifierName;
    private Character deleteYn;

    // Entity 변환
    public Member toEntity() {
        // 직급
        Position position = null;
        if(positionIdx != null) position = Position.builder().idx(positionIdx).build();

        // 역할
        Role role = null;
        if(roleIdx != null) role = Role.builder().idx(roleIdx).build();

        // 등록자
        Member registerMember = null;
        if(register != null) registerMember = Member.builder().idx(register).build();

        // 수정자
        Member modifyMember = null;
        if(modifier != null) modifyMember = Member.builder().idx(modifier).build();

        return Member.builder()
                .idx(idx)
                .profileIdx(profileIdx)
                .positionIdx(positionIdx)
                .position(position)
                .roleIdx(roleIdx)
                .role(role)
                .name(name)
                .account(account)
                .password(password)
                .note(note)
                .disableYn(disableYn)
                .adminYn(adminYn)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .modifierMember(modifyMember)
                .build();
    }

    @Builder
    public MemberDto(Long idx, Long profileIdx, String positionName, Long positionIdx, String roleName, Long roleIdx, String name, String account, String password, String note, Character disableYn, Character adminYn, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName, Character deleteYn) {
        this.idx = idx;
        this.profileIdx = profileIdx;
        this.positionName = positionName;
        this.positionIdx = positionIdx;
        this.roleName = roleName;
        this.roleIdx = roleIdx;
        this.name = name;
        this.account = account;
        this.password = password;
        this.note = note;
        this.disableYn = disableYn;
        this.adminYn = adminYn;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierName = modifierName;
        this.deleteYn = deleteYn;
    }
}
