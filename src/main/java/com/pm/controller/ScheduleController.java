package com.pm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

/**
 * 
 * Schedule Controller
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
@RequestMapping("/schedule")
public class ScheduleController extends com.pm.util.Controller {
    // 스케줄
    @GetMapping(value = {"/", ""})
    public String schedule(HttpServletRequest request, Model model) {
        model = super.setModel(request, model);
        model.addAttribute("curr_date", new Timestamp(System.currentTimeMillis()));

        return "schedule/detail";
    }
}
