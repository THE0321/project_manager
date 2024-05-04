package com.pm.controller;

import com.pm.dto.PositionDto;
import com.pm.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Position Controller
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
@RequestMapping("/position")
public class PositionController extends com.pm.util.Controller {
    private final PositionService positionService;
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // 직급 목록
    @GetMapping(value = {"/", ""})
    public String list(@RequestParam(required = false, value = "name") String name,
                       HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        model.addAttribute("param_name", name);
        model.addAttribute("list", positionService.getList(name));

        return "position/list";
    }

    // 직급 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        if(idx == null) {
            model.addAttribute("detail", new PositionDto());
        } else {
            model.addAttribute("detail", positionService.getOne(idx));
        }

        return "position/detail";
    }
}
