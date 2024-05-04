package com.pm.service;

import com.pm.repository.ActionItemMemberRepository;
import com.pm.repository.ActionItemRepository;
import com.pm.repository.ActionItemStatusRepository;
import org.springframework.stereotype.Service;

/**
 *
 * Action Item Service
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
public class ActionItemService {
    private final ActionItemRepository actionItemRepository;
    private final ActionItemStatusRepository actionItemStatusRepository;
    private final ActionItemMemberRepository actionItemMemberRepository;
    public ActionItemService(ActionItemRepository actionItemRepository, ActionItemStatusRepository actionItemStatusRepository, ActionItemMemberRepository actionItemMemberRepository) {
        this.actionItemRepository = actionItemRepository;
        this.actionItemStatusRepository = actionItemStatusRepository;
        this.actionItemMemberRepository = actionItemMemberRepository;
    }
}
