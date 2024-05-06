package com.pm.service;

import com.pm.dto.ActionItemDto;
import com.pm.dto.ActionItemMemberDto;
import com.pm.dto.ActionItemStatusDto;
import com.pm.entity.ActionItem;
import com.pm.repository.ActionItemMemberRepository;
import com.pm.repository.ActionItemRepository;
import com.pm.repository.ActionItemStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 갯수 조회
    @Transactional
    public Long getCount(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate) {
        title = title == null ? "" : title;

        return actionItemRepository.countByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(projectIdx, directoryIdx, title, statusIdx, startDate, endDate);
    }

    // 목록 조회(전체)
    @Transactional
    public List<ActionItemDto> getListAll(Long projectIdx, String title) {
        title = title == null ? "" : title;

        List<ActionItemDto> resultList = new ArrayList<>();
        actionItemRepository.findByProjectIdxAndTitleContainsOrderByTitle(projectIdx, title).forEach(actionItem -> {
            resultList.add(actionItem.toDto());
        });

        return resultList;
    }

    // 목록 조회
    @Transactional
    public List<ActionItemDto> getList(Long projectIdx, Long directoryIdx, String title, Long statusIdx, Date startDate, Date endDate, Long page) {
        title = title == null ? "" : title;

        List<ActionItemDto> resultList = new ArrayList<>();
        actionItemRepository.findByTitleContainAndStatusIdxAndStartDateAfterAndEndDateOrderByIdxDesc(projectIdx, directoryIdx, title, statusIdx, startDate, endDate).forEach(actionItem -> {
            resultList.add(actionItem.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public ActionItemDto getOne(Long idx) {
        return actionItemRepository.findById(idx).map(ActionItem::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(ActionItemDto actionItemDto) {
        return actionItemRepository.save(actionItemDto.toEntity()).getIdx();
    }

    // 담당자 조회
    @Transactional
    public List<ActionItemMemberDto> getMemberList(Long actionItemIdx) {
        List<ActionItemMemberDto> resultList = new ArrayList<>();
        actionItemMemberRepository.findByActionItemIdx(actionItemIdx).forEach(actionItemMember -> {
            resultList.add(actionItemMember.toDto());
        });

        return resultList;
    }

    // 담당자 추가
    @Transactional
    public void saveMember(Long actionItemIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(memberIdx -> {
            actionItemMemberRepository.save(ActionItemMemberDto.builder().actionItemIdx(actionItemIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }

    // 담당자 삭제
    @Transactional
    public void deleteMember(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(actionItemMemberRepository::deleteById);
    }

    // 상태 조회
    @Transactional
    public List<ActionItemStatusDto> getStatus() {
        List<ActionItemStatusDto> resultList = new ArrayList<>();
        actionItemStatusRepository.findAll().forEach(actionItemStatus -> {
            resultList.add(actionItemStatus.toDto());
        });

        return resultList;
    }
}
