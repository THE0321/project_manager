package com.pm.api;

import com.pm.dto.RiskDto;
import com.pm.service.RiskService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 *
 * Risk REST Controller
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
@RequestMapping("/api/risk")
public class RiskRestController extends Controller {
    private final RiskService riskService;
    public RiskRestController(RiskService riskService) {
        this.riskService = riskService;
    }

    // 리스크 저장
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

        Long loginIdx = super.getLoginData(request).getIdx();

        RiskDto riskDto;
        if(idx == null) {
            riskDto = RiskDto.builder()
                    .title(title)
                    .description(description)
                    .statusIdx(statusIdx)
                    .register(loginIdx)
                    .build();
        } else {
            riskDto = riskService.getOne(idx);
            riskDto.setTitle(title);
            riskDto.setDescription(description);
            riskDto.setStatusIdx(statusIdx);
            riskDto.setModifier(loginIdx);
            riskDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        }

        // 리스크 저장
        Long result = riskService.saveItem(riskDto);

        // 액션 아이템 매핑 저장
        if(deleteActionItemList != null && deleteActionItemList.length > 0) {
            riskService.deleteMapping(deleteActionItemList);
        }

        if(actionItemList != null && actionItemList.length > 0) {
            riskService.saveActionItemMapping(result, actionItemList, loginIdx);
        }

        // 테스트 케이스 매핑 저장
        if(deleteTestCaseList != null && deleteTestCaseList.length > 0) {
            riskService.deleteMapping(deleteTestCaseList);
        }

        if(testCaseList != null && testCaseList.length > 0) {
            riskService.saveTestCaseMapping(result, testCaseList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 리스크 상태 변경
    @PostMapping("/change_status")
    public ResponseData changeStatus(@RequestParam(required = false) Long idx,
                                     @RequestParam(required = false, value = "status_idx") Long statusIdx,
                                     HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        RiskDto riskDto = riskService.getOne(idx);
        riskDto.setStatusIdx(statusIdx);
        riskDto.setModifier(loginIdx);
        riskDto.setModifyDate(new Timestamp(System.currentTimeMillis()));

        riskService.saveItem(riskDto);

        return new ResponseData(true, "저장되었습니다.", null);
    }
}
