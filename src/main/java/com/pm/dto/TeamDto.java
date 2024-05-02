package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.Team;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * team DTO
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeamDto {
    private Long idx;
    private String name;
    private Timestamp registDate;
    private Long register;
    private String registerName;

    // Entity 변환
    public Team toEntity() {
        Member registerMember = null;
        if(register != null)  registerMember = Member.builder().idx(register).build();

        return Team.builder()
                .idx(idx)
                .name(name)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .build();
    }

    @Builder
    public TeamDto(Long idx, String name, Timestamp registDate, Long register, String registerName) {
        this.idx = idx;
        this.name = name;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
    }
}
