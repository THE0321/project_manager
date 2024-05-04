package com.pm.repository;

import com.pm.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * directory JPA  Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}
