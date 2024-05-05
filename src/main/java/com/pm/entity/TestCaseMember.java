package com.pm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;

/**
 * 
 * test_case_member Entity
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
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test_case_member")
public class TestCaseMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "test_case_idx", insertable = false, updatable=false)
    private Long testCaseIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_idx", referencedColumnName = "idx")
    private TestCase testCase;

    @Column(name = "member_idx", insertable = false, updatable=false)
    private Long memberIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", referencedColumnName = "idx")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp registDate;

    @Column
    private Long register;

    @Builder
    public TestCaseMember(Long idx, Long testCaseIdx, TestCase testCase, Long memberIdx, Member member, Timestamp registDate, Long register) {
        this.idx = idx;
        this.testCaseIdx = testCaseIdx;
        this.testCase = testCase;
        this.memberIdx = memberIdx;
        this.member = member;
        this.registDate = registDate == null ? new Timestamp(System.currentTimeMillis()) : registDate;
        this.register = register;
    }
}
