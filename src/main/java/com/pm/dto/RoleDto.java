package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.Role;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * role DTO
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
public class RoleDto {
    private Long idx;
    private String name;
    private Timestamp registDate;
    private Long register;
    private String registerName;

    // Entity 변환
    public Role toEntity() {
        Member registerMember = null;
        if(register != null) registerMember = Member.builder().idx(register).build();

        return Role.builder()
                .idx(idx)
                .name(name)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .build();
    }

    @Builder
    public RoleDto(Long idx, String name, Timestamp registDate, Long register, String registerName) {
        this.idx = idx;
        this.name = name;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
    }
}
