package com.pm.controller;

import com.pm.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "name") String name,
                       HttpServletRequest request, Model model) {
        model.addAttribute("param_name", name);
        model.addAttribute("list", roleService.getList(name));

        return "role/list";
    }
}
