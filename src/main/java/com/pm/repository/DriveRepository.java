package com.pm.repository;

import com.pm.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 
 * drive JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
public interface DriveRepository extends JpaRepository<Drive, Long> {
    List<Drive> findAllByProjectIdxOrderByIdxDesc(Long projectIdx);
}
