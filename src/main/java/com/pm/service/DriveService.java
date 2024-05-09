package com.pm.service;

import com.pm.dto.DriveDto;
import com.pm.repository.DriveRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Drive Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@Service
public class DriveService {
    private final DriveRepository driveRepository;
    public DriveService(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    // 목록 조회
    public List<DriveDto> getList(Long projectIdx) {
        List<DriveDto> resultList = new ArrayList<>();
        driveRepository.findAllByProjectIdxOrderByIdxDesc(projectIdx).forEach(drive -> {
            resultList.add(drive.toDto());
        });

        return resultList;
    }

    // 생성
    public Long saveItem(DriveDto driveDto) {
        return driveRepository.save(driveDto.toEntity()).getIdx();
    }

    // 삭제
    public void deleteItem(Long idx) {
        driveRepository.deleteById(idx);
    }
}
