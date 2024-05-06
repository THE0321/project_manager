package com.pm.service;

import com.pm.dto.TestCaseDto;
import com.pm.dto.TestCaseMappingDto;
import com.pm.dto.TestCaseMemberDto;
import com.pm.dto.TestCaseStatusDto;
import com.pm.entity.TestCase;
import com.pm.repository.TestCaseMappingRepository;
import com.pm.repository.TestCaseMemberRepository;
import com.pm.repository.TestCaseRepository;
import com.pm.repository.TestCaseStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Test Case Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-06
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-06          HTH             최초 등록
 **/
@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final TestCaseMappingRepository testCaseMappingRepository;
    private final TestCaseMemberRepository testCaseMemberRepository;
    private final TestCaseStatusRepository testCaseStatusRepository;
    public TestCaseService(TestCaseRepository testCaseRepository, TestCaseMappingRepository testCaseMappingRepository, TestCaseMemberRepository testCaseMemberRepository, TestCaseStatusRepository testCaseStatusRepository) {
        this.testCaseRepository = testCaseRepository;
        this.testCaseMappingRepository = testCaseMappingRepository;
        this.testCaseMemberRepository = testCaseMemberRepository;
        this.testCaseStatusRepository = testCaseStatusRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate) {
        title = title == null ? "" : title;

        return testCaseRepository.countByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(projectIdx, directoryIdx, title, statusIdx, startDate, endDate);
    }
    
    // 목록 조회
    @Transactional
    public List<TestCaseDto> getList(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate, Long page) {
        title = title == null ? "" : title;

        List<TestCaseDto> resultList = new ArrayList<>();
        testCaseRepository.findByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(projectIdx, directoryIdx, title, statusIdx, startDate, endDate).forEach(testCase -> {
            resultList.add(testCase.toDto());
        });

        return resultList;
    }
    
    // 단건 조회
    @Transactional
    public TestCaseDto getOne(Long idx) {
        return testCaseRepository.findById(idx).map(TestCase::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(TestCaseDto testCaseDto) {
        return testCaseRepository.save(testCaseDto.toEntity()).getIdx();
    }
    
    // 담당자 조회
    @Transactional
    public List<TestCaseMemberDto> getMemberList(Long testCaseIdx) {
        List<TestCaseMemberDto> resultList = new ArrayList<>();
        testCaseMemberRepository.findByTestCaseIdx(testCaseIdx).forEach(testCaseMember -> {
            resultList.add(testCaseMember.toDto());
        });

        return resultList;
    }
    
    // 담당자 저장
    @Transactional
    public void saveMember(Long testCaseIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(memberIdx -> {
            testCaseMemberRepository.save(TestCaseMemberDto.builder().testCaseIdx(testCaseIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }
    
    // 담당자 삭제
    @Transactional
    public void deleteMember(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(testCaseMemberRepository::deleteById);
    }
    
    // 매핑 조회
    @Transactional
    public List<TestCaseMappingDto> getMappingList(Long testCaseIdx) {
        List<TestCaseMappingDto> resultList = new ArrayList<>();
        testCaseMappingRepository.findByTestCaseIdx(testCaseIdx).forEach(testCaseMapping -> {
            resultList.add(testCaseMapping.toDto());
        });

        return resultList;
    }
    
    // 매핑 추가
    @Transactional
    public void saveMapping(Long testCaseIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(actionItemIdx -> {
            testCaseMappingRepository.save(TestCaseMappingDto.builder().testCaseIdx(testCaseIdx).actionItemIdx(actionItemIdx).register(register).build().toEntity());
        });
    }
    
    // 매핑 삭제
    @Transactional
    public void deleteMapping(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(testCaseMappingRepository::deleteById);
    }
    
    // 상태 조회
    @Transactional
    public List<TestCaseStatusDto> getStatus() {
        List<TestCaseStatusDto> resultList = new ArrayList<>();
        testCaseStatusRepository.findAll().forEach(testCaseStatus -> {
            resultList.add(testCaseStatus.toDto());
        });

        return resultList;
    }
}
