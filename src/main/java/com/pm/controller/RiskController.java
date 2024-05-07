package com.pm.controller;

import com.pm.dto.RiskDto;
import com.pm.dto.RiskMappingDto;
import com.pm.service.IssueService;
import com.pm.service.RiskService;
import com.pm.util.Paging;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 *
 * Risk Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-07
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-07          HTH             최초 등록
 **/
@Controller
@RequestMapping("/risk")
public class RiskController extends com.pm.util.Controller {
    private final RiskService riskService;
    private final IssueService issueService;
    public RiskController(RiskService riskService, IssueService issueService) {
        this.riskService = riskService;
        this.issueService = issueService;
    }

    // 리스크 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "title") String title,
                       @RequestParam(required = false, value = "status_idx") Long statusIdx,
                       @RequestParam(required = false, value = "page") Long page,
                       HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("param_title", title);
        model.addAttribute("param_status_idx", statusIdx);

        model.addAttribute("status_list", issueService.getStatus());
        model.addAttribute("list", riskService.getList(3L, title, statusIdx, page));
        model.addAttribute("page", new Paging(page, riskService.getCount(3L, title, statusIdx)));

        return "risk/list";
    }

    // 이슈 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("status_list", issueService.getStatus());

        if(idx == null) {
            model.addAttribute("detail", new RiskDto());
            model.addAttribute("mapping_list", new ArrayList<RiskMappingDto>());
        } else {
            model.addAttribute("detail", riskService.getOne(idx));
            model.addAttribute("mapping_list", riskService.getMappingList(idx));
        }

        return "risk/detail";
    }
}
