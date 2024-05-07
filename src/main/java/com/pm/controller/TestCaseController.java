package com.pm.controller;

import com.pm.dto.TestCaseDto;
import com.pm.dto.TestCaseMappingDto;
import com.pm.dto.TestCaseMemberDto;
import com.pm.service.DirectoryService;
import com.pm.service.TestCaseService;
import com.pm.util.DateFormat;
import com.pm.util.Paging;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;

/**
 * 
 * Test Case Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-06
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-06          HTH             최초 등록
 **/
@Controller
@RequestMapping("/testcase")
public class TestCaseController extends com.pm.util.Controller {
    private final DirectoryService directoryService;
    private final TestCaseService testCaseService;
    public TestCaseController(DirectoryService directoryService, TestCaseService testCaseService) {
        this.directoryService = directoryService;
        this.testCaseService = testCaseService;
    }

    // 테스트 케이스 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "directory_idx") Long directoryIdx,
                       @RequestParam(required = false, value = "title") String title,
                       @RequestParam(required = false, value = "status_idx") Long statusIdx,
                       @RequestParam(required = false, value = "start_date") String startDate,
                       @RequestParam(required = false, value = "end_date") String endDate,
                       @RequestParam(required = false, value = "page") Long page,
                       HttpServletRequest request, Model model) {
        model = super.setModel(request, model);
        DateFormat dateFormat = new DateFormat();

        model.addAttribute("param_directory_idx", directoryIdx);
        model.addAttribute("param_title", title);
        model.addAttribute("param_status_idx", statusIdx);
        model.addAttribute("param_start_date", startDate);
        model.addAttribute("param_end_date", endDate);

        Date sqlStartDate = startDate == null ? null : dateFormat.parseDate(startDate);
        Date sqlEndDate = endDate == null ? null : dateFormat.parseDate(endDate);

        model.addAttribute("status_list", testCaseService.getStatus());
        model.addAttribute("directory_list", directoryService.getList(3L));
        model.addAttribute("list", testCaseService.getList(3L, directoryIdx, title, statusIdx, sqlStartDate, sqlEndDate, page));
        model.addAttribute("page", new Paging(page, testCaseService.getCount(3L, directoryIdx, title, statusIdx, sqlStartDate, sqlEndDate)));

        return "test_case/list";
    }

    // 테스트 케이스 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("status_list", testCaseService.getStatus());
        model.addAttribute("directory_list", directoryService.getList(3L));

        if(idx == null) {
            model.addAttribute("detail", new TestCaseDto());
            model.addAttribute("member_list", new ArrayList<TestCaseMemberDto>());
            model.addAttribute("mapping_list", new ArrayList<TestCaseMappingDto>());
        } else {
            model.addAttribute("detail", testCaseService.getOne(idx));
            model.addAttribute("member_list", testCaseService.getMemberList(idx));
            model.addAttribute("mapping_list", testCaseService.getMappingList(idx));
        }

        return "test_case/detail";
    }
}
