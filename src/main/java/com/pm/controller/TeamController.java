package com.pm.controller;

import com.pm.dto.TeamDto;
import com.pm.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Team Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
@Controller
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "name") String name,
                       HttpServletRequest request, Model model) {
        model.addAttribute("param_name", name);
        model.addAttribute("list", teamService.getList(name));

        return "/team/list";
    }

    // 팀 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        if(idx == null) {
            model.addAttribute("detail", new TeamDto());
        } else {
            model.addAttribute("detail", teamService.getOne(idx));
        }

        return "/team/detail";
    }
}
