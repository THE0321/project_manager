package com.pm.repository;

import com.pm.entity.ActionItemMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * action_item_member JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public interface ActionItemMemberRepository extends JpaRepository<ActionItemMember, Long> {
    @Query("SELECT A " +
            "FROM ActionItemMember A " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.actionItemIdx = :actionItemIdx")
    List<ActionItemMember> findByActionItemIdx(Long actionItemIdx);
}
