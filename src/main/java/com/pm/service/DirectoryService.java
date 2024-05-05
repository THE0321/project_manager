package com.pm.service;

import com.pm.dto.DirectoryDto;
import com.pm.dto.DirectoryStatusDto;
import com.pm.entity.Directory;
import com.pm.repository.DirectoryRepository;
import com.pm.repository.DirectoryStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Directory Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
@Service
public class DirectoryService {
    private DirectoryRepository directoryRepository;
    private DirectoryStatusRepository directoryStatusRepository;
    public DirectoryService(DirectoryRepository directoryRepository, DirectoryStatusRepository directoryStatusRepository) {
        this.directoryRepository = directoryRepository;
        this.directoryStatusRepository = directoryStatusRepository;
    }

    // 목록 조회
    @Transactional
    public List<DirectoryDto> getList(Long projectIdx) {
        List<DirectoryDto> resultList = new ArrayList<>();
        directoryRepository.findByProjectIdxOrderByTitle(projectIdx).forEach(directory -> {
            resultList.add(directory.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public DirectoryDto getOne(Long idx) {
        return directoryRepository.findById(idx).map(Directory::toDto).orElse(null);
    }

    // 저장/수정
    @Transactional
    public Long saveItem(DirectoryDto directoryDto) {
        return directoryRepository.save(directoryDto.toEntity()).getIdx();
    }

    // 상태 조회
    @Transactional
    public List<DirectoryStatusDto> getStatus() {
        List<DirectoryStatusDto> resultList = new ArrayList<>();
        directoryStatusRepository.findAll().forEach(status -> {
            resultList.add(status.toDto());
        });

        return resultList;
    }
}
