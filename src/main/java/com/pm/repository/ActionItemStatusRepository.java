package com.pm.repository;

import com.pm.entity.ActionItemStatus;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * action_item_status JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public interface ActionItemStatusRepository extends CrudRepository<ActionItemStatus, Long> {
}
