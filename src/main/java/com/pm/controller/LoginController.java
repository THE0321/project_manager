package com.pm.controller;

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
public class LoginController {
    // 로그인
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login/login";
    }

    // 비밀번호 찾기
    @GetMapping("/find_password")
    public String findPassword(Model model) {
        return "login/find_password";
    }
}
