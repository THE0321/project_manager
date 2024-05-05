package com.pm.controller;

import com.pm.dto.DirectoryDto;
import com.pm.service.DirectoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Directory Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-05
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-05          HTH             최초 등록
 **/
@Controller
@RequestMapping("/directory")
public class DirectoryController extends com.pm.util.Controller {
    private final DirectoryService directoryService;
    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    // 디렉토리 상세
    @GetMapping(value = {"/detail", "/detail/{idx}"})
    public String detail(@PathVariable(required = false) Long idx,
                         HttpServletRequest request, Model model) {
        model = super.setModel(request, model);
        model.addAttribute("status_list", directoryService.getStatus());

        if(null == idx) {
            model.addAttribute("detail", new DirectoryDto());
        } else {
            model.addAttribute("detail", directoryService.getOne(idx));
        }

        return "directory/detail";
    }
}
