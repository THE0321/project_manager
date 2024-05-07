package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.TestCase;
import com.pm.entity.TestCaseStatus;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * test_case DTO
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestCaseDto {
    private Long idx;
    private Long projectIdx;
    private Long directoryIdx;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long statusIdx;
    private String statusName;
    private Timestamp statusDate;
    private Timestamp registDate;
    private Long register;
    private String registerName;
    private Timestamp modifyDate;
    private Long modifier;
    private String modifierName;

    // Entity 변환
    public TestCase toEntity() {
        TestCaseStatus testCaseStatus = statusIdx == null ? null : TestCaseStatus.builder().idx(statusIdx).build();
        Member registerMember = register == null ? null : Member.builder().idx(register).build();
        Member modifierMember = modifier == null ? null : Member.builder().idx(modifier).build();

        return TestCase.builder()
                .idx(idx)
                .projectIdx(projectIdx)
                .directoryIdx(directoryIdx)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .statusIdx(statusIdx)
                .testCaseStatus(testCaseStatus)
                .statusDate(statusDate)
                .registDate(registDate)
                .register(register)
                .registerMember(registerMember)
                .modifyDate(modifyDate)
                .modifier(modifier)
                .modifierMember(modifierMember)
                .build();
    }

    @Builder
    public TestCaseDto(Long idx, Long projectIdx, Long directoryIdx, String title, String description, Date startDate, Date endDate, Long statusIdx, String statusName, Timestamp statusDate, Timestamp registDate, Long register, String registerName, Timestamp modifyDate, Long modifier, String modifierName) {
        this.idx = idx;
        this.projectIdx = projectIdx;
        this.directoryIdx = directoryIdx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusIdx = statusIdx;
        this.statusName = statusName;
        this.statusDate = statusDate;
        this.registDate = registDate;
        this.register = register;
        this.registerName = registerName;
        this.modifyDate = modifyDate;
        this.modifier = modifier;
        this.modifierName = modifierName;
    }
}
