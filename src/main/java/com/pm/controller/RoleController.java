package com.pm.controller;

import com.pm.dto.MemberDto;
import com.pm.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Role Controller
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
@RequestMapping("/role")
public class RoleController extends com.pm.util.Controller {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // 역할 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "name") String name,
                       HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("param_name", name);
        model.addAttribute("list", roleService.getList(name));

        return "role/list";
    }

    // 역할 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        if(idx == null) {
            model.addAttribute("detail", new MemberDto());
        } else {
            model.addAttribute("detail", roleService.getOne(idx));
        }

        return "role/detail";
    }
}
