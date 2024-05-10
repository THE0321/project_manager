package com.pm.controller;

import com.pm.service.DriveService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Drive Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@Controller
@RequestMapping("/drive")
public class DriveController extends com.pm.util.Controller {
    private final DriveService driveService;
    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    // 드라이브 목록
    @GetMapping(value = {"/", ""})
    public String list(HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        Long projectIdx = super.getProjectData(request);

        model.addAttribute("list", driveService.getList(projectIdx));

        return "drive/list";
    }
}
