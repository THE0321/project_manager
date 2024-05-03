package com.pm.controller;

import com.pm.dto.ProjectDto;
import com.pm.dto.ProjectMemberDto;
import com.pm.service.ProjectService;
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
 * Project Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // 프로젝트 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "title") String title,
                       @RequestParam(required = false, value = "status_idx") Long statusIdx,
                       @RequestParam(required = false, value = "start_date") String startDate,
                       @RequestParam(required = false, value = "end_date") String endDate,
                       @RequestParam(required = false, value = "page") Long page,
                       HttpServletRequest request, Model model) {
        model.addAttribute("param_title", title);
        model.addAttribute("param_status_idx", statusIdx);
        model.addAttribute("param_start_date", startDate);
        model.addAttribute("param_end_date", endDate);

        model.addAttribute("status_list", projectService.getStatus());
        model.addAttribute("list", projectService.getList(title, statusIdx, startDate, endDate, page));
        model.addAttribute("page", new Paging(page, projectService.getCount(title, statusIdx, startDate, endDate)));

        return "/project/list";
    }

    // 프로젝트 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model.addAttribute("status_list", projectService.getStatus());

        if(idx == null) {
            model.addAttribute("detail", new ProjectDto());
            model.addAttribute("member_list", new ArrayList<ProjectMemberDto>());
        } else {
            model.addAttribute("detail", projectService.getOne(idx));
            model.addAttribute("member_list", projectService.getMemberList(idx));
        }

        return "/project/detail";
    }
}
