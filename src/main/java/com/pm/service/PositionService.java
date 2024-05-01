package com.pm.service;

import com.pm.dto.PositionDto;
import com.pm.entity.Position;
import com.pm.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Position Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
@Service
public class PositionService {
    private final PositionRepository positionRepository;
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    // 목록 조회
    @Transactional
    public List<PositionDto> getList(String name) {
        List<PositionDto> resultList = new ArrayList<>();
        positionRepository.findByNameContainingOrderByOrderNumberAsc(name).forEach(position -> {
            resultList.add(position.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public PositionDto getOne(Long idx) {
        return positionRepository.findById(idx).map(Position::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(PositionDto positionDto) {
        return positionRepository.save(positionDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        positionRepository.deleteById(idx);
    }
}
