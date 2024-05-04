package com.pm.repository;

import com.pm.entity.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * action_item JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {
}
