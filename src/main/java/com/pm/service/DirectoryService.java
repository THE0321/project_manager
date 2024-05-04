package com.pm.service;

import com.pm.repository.DirectoryRepository;
import com.pm.repository.DirectoryStatusRepository;
import org.springframework.stereotype.Service;

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
}
