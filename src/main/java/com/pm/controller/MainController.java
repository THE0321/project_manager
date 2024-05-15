package com.pm.controller;

import com.pm.service.ActionItemService;
import com.pm.service.IssueService;
import com.pm.service.RiskService;
import com.pm.service.TestCaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * Main Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-15
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-15          HTH             최초 등록
 **/
@Controller
public class MainController extends com.pm.util.Controller {
    private final ActionItemService actionItemService;
    private final TestCaseService testCaseService;
    private final IssueService issueService;
    private final RiskService riskService;
    public MainController(ActionItemService actionItemService, TestCaseService testCaseService, IssueService issueService, RiskService riskService) {
        this.actionItemService = actionItemService;
        this.testCaseService = testCaseService;
        this.issueService = issueService;
        this.riskService = riskService;
    }

    // 메인 페이지
    @GetMapping(value = {"/", ""})
    public String index(HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        Long projectIdx = super.getProjectData(request);

        if(projectIdx < 1) {
            return "redirect:/project";
        }

        model.addAttribute("action_item_status_list", actionItemService.getStatus());
        model.addAttribute("test_case_status_list", testCaseService.getStatus());
        model.addAttribute("issue_list", issueService.getListRecently(projectIdx));
        model.addAttribute("risk_list", riskService.getListRecently(projectIdx));

        return "index";
    }
}
