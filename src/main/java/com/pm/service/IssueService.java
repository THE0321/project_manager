package com.pm.service;

import com.pm.dto.IssueDto;
import com.pm.dto.IssueMappingDto;
import com.pm.dto.IssueStatusDto;
import com.pm.entity.Issue;
import com.pm.repository.IssueMappingRepository;
import com.pm.repository.IssueRepository;
import com.pm.repository.IssueStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Issue Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final IssueMappingRepository issueMappingRepository;
    private final IssueStatusRepository issueStatusRepository;
    public IssueService(IssueRepository issueRepository, IssueStatusRepository issueStatusRepository, IssueMappingRepository issueMappingRepository) {
        this.issueRepository = issueRepository;
        this.issueMappingRepository = issueMappingRepository;
        this.issueStatusRepository = issueStatusRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(Long projectIdx, String title, Long statusIdx) {
        title = title == null ? "" : title;

        return issueRepository.countByTitleContainingAndStatusIdxOrderByIdxDesc(projectIdx, title, statusIdx);
    }

    // 목록 조회
    @Transactional
    public List<IssueDto> getList(Long projectIdx, String title, Long statusIdx, Long page) {
        title = title == null ? "" : title;

        List<IssueDto> resultList = new ArrayList<>();
        issueRepository.findByTitleContainingAndStatusIdxOrderByIdxDesc(projectIdx, title, statusIdx).forEach(issue -> {
            resultList.add(issue.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public IssueDto getOne(Long idx) {
        return issueRepository.findById(idx).map(Issue::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(IssueDto issueDto) {
        return issueRepository.save(issueDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        issueRepository.deleteById(idx);
    }

    // 매핑 조회
    @Transactional
    public List<IssueMappingDto> getMappingList(Long issueIdx) {
        List<IssueMappingDto> resultList = new ArrayList<>();
        issueMappingRepository.findByIssueIdx(issueIdx).forEach(issueMapping -> {
            resultList.add(issueMapping.toDto());
        });

        return resultList;
    }

    // 매핑 조회 (액션 아이템)
    @Transactional
    public List<IssueMappingDto> getActionItemMappingList(Long actionItemIdx) {
        List<IssueMappingDto> resultList = new ArrayList<>();
        issueMappingRepository.findByActionItemIdx(actionItemIdx).forEach(issueMapping -> {
            resultList.add(issueMapping.toDto());
        });

        return resultList;
    }

    // 매핑 조회 (테스트 케이스)
    @Transactional
    public List<IssueMappingDto> getTestCaseMappingList(Long testCaseIdx) {
        List<IssueMappingDto> resultList = new ArrayList<>();
        issueMappingRepository.findByTestCaseIdx(testCaseIdx).forEach(issueMapping -> {
            resultList.add(issueMapping.toDto());
        });

        return resultList;
    }

    // 매핑 추가 (액션 아이템)
    @Transactional
    public void saveActionItemMapping(Long issueIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(actionItemIdx -> {
            issueMappingRepository.save(IssueMappingDto.builder().issueIdx(issueIdx).actionItemIdx(actionItemIdx).register(register).build().toEntity());
        });
    }

    // 매핑 추가 (테스트 케이스)
    @Transactional
    public void saveTestCaseMapping(Long issueIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(testCaseIdx -> {
            issueMappingRepository.save(IssueMappingDto.builder().issueIdx(issueIdx).testCaseIdx(testCaseIdx).register(register).build().toEntity());
        });
    }

    // 매핑 삭제
    @Transactional
    public void deleteMapping(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(issueMappingRepository::deleteById);
    }

    // 상태 조회
    @Transactional
    public List<IssueStatusDto> getStatus() {
        List<IssueStatusDto> resultList = new ArrayList<>();
        issueStatusRepository.findAll().forEach(issueStatus -> {
            resultList.add(issueStatus.toDto());
        });

        return resultList;
    }
}
