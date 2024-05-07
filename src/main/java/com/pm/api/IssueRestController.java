package com.pm.api;

import com.pm.dto.IssueDto;
import com.pm.service.IssueService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * 
 * Issue REST Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/issue")
public class IssueRestController {
    private final IssueService issueService;
    public IssueRestController(IssueService issueService) {
        this.issueService = issueService;
    }

    // 이슈 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "description") String description,
                             @RequestParam(required = false, value = "status_idx") Long statusIdx,
                             @RequestParam(required = false, value = "action_item_list") Long[] actionItemList,
                             @RequestParam(required = false, value = "delete_action_item_list") Long[] deleteActionItemList,
                             @RequestParam(required = false, value = "test_case_list") Long[] testCaseList,
                             @RequestParam(required = false, value = "delete_test_case_list") Long[] deleteTestCaseList,
                             HttpServletRequest request) {
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "리스크 제목을 입력해주세요.", null);
        }

        IssueDto issueDto;
        if(idx == null) {
            issueDto = IssueDto.builder()
                    .title(title)
                    .description(description)
                    .statusIdx(statusIdx)
                    .register(1L)
                    .build();
        } else {
            issueDto = issueService.getOne(idx);
            issueDto.setTitle(title);
            issueDto.setDescription(description);
            issueDto.setStatusIdx(statusIdx);
            issueDto.setModifier(1L);
            issueDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        }

        // 이슈 저장
        Long result = issueService.saveItem(issueDto);

        // 액션 아이템 매핑 저장
        if(deleteActionItemList != null && deleteActionItemList.length > 0) {
            issueService.deleteMapping(deleteActionItemList);
        }

        if(actionItemList != null && actionItemList.length > 0) {
            issueService.saveActionItemMapping(result, actionItemList, 1L);
        }

        // 테스트 케이스 매핑 저장
        if(deleteTestCaseList != null && deleteTestCaseList.length > 0) {
            issueService.deleteMapping(deleteTestCaseList);
        }

        if(testCaseList != null && testCaseList.length > 0) {
            issueService.saveTestCaseMapping(result, testCaseList, 1L);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 이슈 상태 변경
    @PostMapping("/change_status")
    public ResponseData changeStatus(@RequestParam(required = false) Long idx,
                                     @RequestParam(required = false, value = "status_idx") Long statusIdx,
                                     HttpServletRequest request) {
        IssueDto issueDto = issueService.getOne(idx);
        issueDto.setStatusIdx(statusIdx);
        issueDto.setModifier(1L);
        issueDto.setModifyDate(new Timestamp(System.currentTimeMillis()));

        issueService.saveItem(issueDto);

        return new ResponseData(true, "저장되었습니다.", null);
    }
}
