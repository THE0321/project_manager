package com.pm.dto;

import com.pm.entity.Member;
import com.pm.entity.TestCase;
import com.pm.entity.TestCaseMember;
import lombok.*;

import java.sql.Timestamp;

/**
 *
 * test_case_member DTO
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
public class TestCaseMemberDto {
    private Long idx;
    private Long testCaseIdx;
    private String testCaseName;
    private Long memberIdx;
    private String memberName;
    private Timestamp registDate;
    private Long register;

    // Entity 변환
    public TestCaseMember toEntity() {
        TestCase testCase = testCaseIdx == null ? null : TestCase.builder().idx(testCaseIdx).build();
        Member member = memberIdx == null ? null : Member.builder().idx(memberIdx).build();

        return TestCaseMember.builder()
                .idx(idx)
                .testCaseIdx(testCaseIdx)
                .testCase(testCase)
                .memberIdx(memberIdx)
                .member(member)
                .registDate(registDate)
                .register(register)
                .build();
    }

    @Builder
    public TestCaseMemberDto(Long idx, Long testCaseIdx, String testCaseName, Long memberIdx, String memberName, Timestamp registDate, Long register) {
        this.idx = idx;
        this.testCaseIdx = testCaseIdx;
        this.testCaseName = testCaseName;
        this.memberIdx = memberIdx;
        this.memberName = memberName;
        this.registDate = registDate;
        this.register = register;
    }
}
