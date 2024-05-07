package com.pm.repository;

import com.pm.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * file JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
public interface FileRepository extends JpaRepository<File, Long> {

}
