package com.pm.service;

import com.pm.dto.RiskDto;
import com.pm.dto.RiskMappingDto;
import com.pm.entity.Risk;
import com.pm.entity.RiskMapping;
import com.pm.repository.RiskMappingRepository;
import com.pm.repository.RiskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Risk Service
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
public class RiskService {
    private final RiskRepository riskRepository;
    private final RiskMappingRepository riskMappingRepository;
    public RiskService(RiskRepository riskRepository, RiskMappingRepository riskMappingRepository) {
        this.riskRepository = riskRepository;
        this.riskMappingRepository = riskMappingRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(Long projectIdx, String title, Long statusIdx) {
        title = title == null ? "" : title;

        return riskRepository.countByTitleContainingAndStatusIdxOrderByIdxDesc(projectIdx, title, statusIdx);
    }

    // 목록 조회
    @Transactional
    public List<RiskDto> getList(Long projectIdx, String title, Long statusIdx, Long page) {
        title = title == null ? "" : title;

        List<RiskDto> resultList = new ArrayList<>();
        riskRepository.findByTitleContainingAndStatusIdxOrderByIdxDesc(projectIdx, title, statusIdx).forEach(risk -> {
            resultList.add(risk.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public RiskDto getOne(Long idx) {
        return riskRepository.findById(idx).map(Risk::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(RiskDto riskDto) {
        return riskRepository.save(riskDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        riskRepository.deleteById(idx);
    }

    // 매핑 조회
    @Transactional
    public List<RiskMappingDto> getMappingList(Long riskIdx) {
        List<RiskMappingDto> resultList = new ArrayList<>();

        riskMappingRepository.findByRiskIdx(riskIdx).forEach(riskMapping -> {
            resultList.add(riskMapping.toDto());
        });

        return resultList;
    }

    // 매핑 조회 (액션 아이템)
    @Transactional
    public List<RiskMappingDto> getActionItemMappingList(Long actionItemIdx) {
        List<RiskMappingDto> resultList = new ArrayList<>();

        riskMappingRepository.findByActionItemIdx(actionItemIdx).forEach(riskMapping -> {
            resultList.add(riskMapping.toDto());
        });

        return resultList;
    }

    // 매핑 조회 (테스트 케이스)
    @Transactional
    public List<RiskMappingDto> getTestCaseMappingList(Long testCaseIdx) {
        List<RiskMappingDto> resultList = new ArrayList<>();

        riskMappingRepository.findByTestCaseIdx(testCaseIdx).forEach(riskMapping -> {
            resultList.add(riskMapping.toDto());
        });

        return resultList;
    }

    // 매핑 추가 (액션 아이템)
    @Transactional
    public void saveActionItemMapping(Long riskIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(actionItemIdx -> {
            riskMappingRepository.save(RiskMappingDto.builder().riskIdx(riskIdx).actionItemIdx(actionItemIdx).register(register).build().toEntity());
        });
    }

    // 매핑 추가 (테스트 케이스)
    @Transactional
    public void saveTestCaseMapping(Long riskIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(testCaseIdx -> {
            riskMappingRepository.save(RiskMappingDto.builder().riskIdx(riskIdx).testCaseIdx(testCaseIdx).register(register).build().toEntity());
        });
    }

    // 매핑 삭제
    @Transactional
    public void deleteMapping(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(riskMappingRepository::deleteById);
    }
}
