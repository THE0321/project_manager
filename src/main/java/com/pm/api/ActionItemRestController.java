package com.pm.api;

import com.pm.dto.ActionItemDto;
import com.pm.service.ActionItemService;
import com.pm.util.DateFormat;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * 
 * Action Item REST Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/action")
public class ActionItemRestController {
    private final ActionItemService actionItemService;
    public ActionItemRestController(ActionItemService actionItemService) {
        this.actionItemService = actionItemService;
    }

    // 액션 아이템 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "directory_idx") Long directoryIdx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "description") String description,
                             @RequestParam(required = false, value = "status_idx") Long statusIdx,
                             @RequestParam(required = false, value = "start_date") String startDate,
                             @RequestParam(required = false, value = "end_date") String endDate,
                             @RequestParam(required = false, value = "member_list") Long[] memberList,
                             @RequestParam(required = false, value = "delete_list") Long[] deleteMemberList,
                             HttpServletRequest request) {
        if(directoryIdx == null) {
            return new ResponseData(false, "디렉토리를 선택해주세요.", null);
        }
        
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "액션 아이템 이름을 입력해주세요.", null);
        }

        DateFormat dateFormat = new DateFormat();
        ActionItemDto actionItemDto;
        if(idx == null) {
            actionItemDto = ActionItemDto.builder()
                    .projectIdx(3L)
                    .directoryIdx(directoryIdx)
                    .title(title)
                    .description(description)
                    .statusIdx(statusIdx)
                    .startDate(startDate == null ? null : dateFormat.parseDate(startDate))
                    .endDate(endDate == null ? null : dateFormat.parseDate(endDate))
                    .register(1L)
                    .build();
        } else {
            actionItemDto = actionItemService.getOne(idx);
            actionItemDto.setDirectoryIdx(directoryIdx);
            actionItemDto.setTitle(title);
            actionItemDto.setDescription(description);
            actionItemDto.setStartDate(startDate == null ? null : dateFormat.parseDate(startDate));
            actionItemDto.setEndDate(endDate == null ? null : dateFormat.parseDate(endDate));
            actionItemDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            actionItemDto.setModifier(1L);

            if(actionItemDto.getStatusIdx() == null) {
                if(statusIdx != null) {
                    actionItemDto.setStatusIdx(statusIdx);
                    actionItemDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
                }
            } else if(!actionItemDto.getStatusIdx().equals(statusIdx)) {
                actionItemDto.setStatusIdx(statusIdx);
                actionItemDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        }

        // 액션 아이템 저장
        Long result = actionItemService.saveItem(actionItemDto);

        // 액션 아이템 담당자 저장
        if(deleteMemberList != null && deleteMemberList.length > 0) {
            actionItemService.deleteMember(deleteMemberList);
        }

        if(memberList != null && memberList.length > 0) {
            actionItemService.saveMember(result, memberList, 1L);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 액션 아이템 상태 변경
    @PostMapping("/change_status")
    public ResponseData changeStatus(@RequestParam(required = false) Long idx,
                                     @RequestParam(required = false, value = "status_idx") Long statusIdx,
                                     HttpServletRequest request) {
        ActionItemDto actionItemDto = actionItemService.getOne(idx);
        actionItemDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        actionItemDto.setModifier(1L);

        if(actionItemDto.getStatusIdx() == null) {
            if(statusIdx != null) {
                actionItemDto.setStatusIdx(statusIdx);
                actionItemDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        } else if(!actionItemDto.getStatusIdx().equals(statusIdx)) {
            actionItemDto.setStatusIdx(statusIdx);
            actionItemDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
        }

        actionItemService.saveItem(actionItemDto);

        return new ResponseData(true, "저장되었습니다.", null);
    }

    // 액션 아이템 목록 조회
    @GetMapping("/get_list")
    public ResponseData getList(@RequestParam(required = false, value = "name") String name,
                                HttpServletRequest request) {
        return new ResponseData(true, "조회했습니다.", actionItemService.getListAll(3L, name));
    }
}
