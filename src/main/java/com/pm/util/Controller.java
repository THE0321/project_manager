package com.pm.util;

import com.pm.service.MemberService;
import com.pm.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

/**
 * 
 * Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-04
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-04          HTH             최초 등록
 **/
public class Controller {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    // 공통 변수 세팅
    public Model setModel(HttpServletRequest request, Model model) {
        // TODO: 로그인 정보 기준 조회 + 세션 저장
        model.addAttribute("session_info", memberService.getOneWithout(1L));
        model.addAttribute("session_project_list", projectService.getMyProject(1L));

        String[] url = request.getRequestURI().split("/");
        model.addAttribute("current_page", url.length > 1 ? url[1] : "");

        return model;
    }
}
