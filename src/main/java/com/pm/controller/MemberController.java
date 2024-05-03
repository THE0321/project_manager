package com.pm.controller;

import com.pm.dto.MemberDto;
import com.pm.dto.TeamMemberDto;
import com.pm.service.MemberService;
import com.pm.service.PositionService;
import com.pm.service.RoleService;
import com.pm.service.TeamService;
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
 * Member Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PositionService positionService;
    private final RoleService roleService;
    public MemberController(MemberService memberService, PositionService positionService, RoleService roleService) {
        this.memberService = memberService;
        this.positionService = positionService;
        this.roleService = roleService;
    }

    // 유저 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "name") String name,
                       @RequestParam(required = false, value = "account") String account,
                       @RequestParam(required = false, value = "position_idx") Long positionIdx,
                       @RequestParam(required = false, value = "role_idx") Long roleIdx,
                       @RequestParam(required = false, value = "page") Long page,
                       HttpServletRequest request, Model model) {
        model.addAttribute("param_name", name);
        model.addAttribute("param_account", account);
        model.addAttribute("param_position_idx", positionIdx);
        model.addAttribute("param_role_idx", roleIdx);

        model.addAttribute("position_list", positionService.getList(null));
        model.addAttribute("role_list", roleService.getList(null));
        model.addAttribute("list", memberService.getList(name, account, positionIdx, roleIdx, page));
        model.addAttribute("page", new Paging(page, memberService.getCount(name, account, positionIdx, roleIdx)));

        return "/member/list";
    }

    // 유저 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                       HttpServletRequest request, Model model) {
        model.addAttribute("position_list", positionService.getList(null));
        model.addAttribute("role_list", roleService.getList(null));

        if(idx == null) {
            model.addAttribute("detail", new MemberDto());
            model.addAttribute("team_list", new ArrayList<TeamMemberDto>());
        } else {
            model.addAttribute("detail", memberService.getOneWithout(idx));
            model.addAttribute("team_list", memberService.getMyTeam(idx));
        }

        return "/member/detail";
    }
}
