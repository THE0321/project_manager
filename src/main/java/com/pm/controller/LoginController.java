package com.pm.controller;

import com.pm.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Login Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-10
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-10          HTH             최초 등록
 **/
@Controller
public class LoginController extends com.pm.util.Controller {
    private final MemberService memberService;
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login/login";
    }

    // 비밀번호 찾기
    @GetMapping("/my_info")
    public String findPassword(HttpServletRequest request, Model model) {
        model = super.setModel(request, model);

        Long loginIdx = super.getLoginData(request).getIdx();

        model.addAttribute("detail", memberService.getOneWithout(loginIdx));
        model.addAttribute("team_list", memberService.getMyTeam(loginIdx));

        return "login/my_info";
    }
}
