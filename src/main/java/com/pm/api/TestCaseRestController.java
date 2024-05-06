package com.pm.api;

import com.pm.dto.TestCaseDto;
import com.pm.service.TestCaseService;
import com.pm.util.DateFormat;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * 
 * Test Case REST Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-06
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-06          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/testcase")
public class TestCaseRestController {
    private final TestCaseService testCaseService;
    public TestCaseRestController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    // 테스트 케이스 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "directory_idx") Long directoryIdx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "description") String description,
                             @RequestParam(required = false, value = "status_idx") Long statusIdx,
                             @RequestParam(required = false, value = "start_date") String startDate,
                             @RequestParam(required = false, value = "end_date") String endDate,
                             @RequestParam(required = false, value = "member_list") Long[] memberList,
                             @RequestParam(required = false, value = "delete_member_list") Long[] deleteMemberList,
                             @RequestParam(required = false, value = "action_item_list") Long[] actionItemList,
                             @RequestParam(required = false, value = "delete_action_item_list") Long[] deleteActionItemList,
                             HttpServletRequest request) {
        if(directoryIdx == null) {
            return new ResponseData(false, "디렉토리를 선택해주세요.", null);
        }

        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "액션 아이템 이름을 입력해주세요.", null);
        }

        DateFormat dateFormat = new DateFormat();
        TestCaseDto testCaseDto;
        if(idx == null) {
            testCaseDto = TestCaseDto.builder()
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
            testCaseDto = testCaseService.getOne(idx);
            testCaseDto.setDirectoryIdx(directoryIdx);
            testCaseDto.setTitle(title);
            testCaseDto.setDescription(description);
            testCaseDto.setStartDate(startDate == null ? null : dateFormat.parseDate(startDate));
            testCaseDto.setEndDate(endDate == null ? null : dateFormat.parseDate(endDate));
            testCaseDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            testCaseDto.setModifier(1L);

            if(testCaseDto.getStatusIdx() == null) {
                if(statusIdx != null) {
                    testCaseDto.setStatusIdx(statusIdx);
                    testCaseDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
                }
            } else if(!testCaseDto.getStatusIdx().equals(statusIdx)) {
                testCaseDto.setStatusIdx(statusIdx);
                testCaseDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        }

        // 테스트 케이스 저장
        Long result = testCaseService.saveItem(testCaseDto);

        // 테스트 케이스 담당자 저장
        if(deleteMemberList != null && deleteMemberList.length > 0) {
            testCaseService.deleteMember(deleteMemberList);
        }

        if(memberList != null && memberList.length > 0) {
            testCaseService.saveMember(result, memberList, 1L);
        }

        // 액션 아이템 매핑 저장
        if(deleteActionItemList != null && deleteActionItemList.length > 0) {
            testCaseService.deleteMapping(deleteActionItemList);
        }

        if(actionItemList != null && actionItemList.length > 0) {
            testCaseService.saveMapping(result, actionItemList, 1L);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 테스트 케이스 상태 변경
    @PostMapping("/change_status")
    public ResponseData changeStatus(@RequestParam(required = false) Long idx,
                                     @RequestParam(required = false, value = "status_idx") Long statusIdx,
                                     HttpServletRequest request) {
        TestCaseDto testCaseDto = testCaseService.getOne(idx);
        testCaseDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        testCaseDto.setModifier(1L);

        if(testCaseDto.getStatusIdx() == null) {
            if(statusIdx != null) {
                testCaseDto.setStatusIdx(statusIdx);
                testCaseDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        } else if(!testCaseDto.getStatusIdx().equals(statusIdx)) {
            testCaseDto.setStatusIdx(statusIdx);
            testCaseDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
        }

        testCaseService.saveItem(testCaseDto);

        return new ResponseData(true, "저장되었습니다.", null);
    }
}
