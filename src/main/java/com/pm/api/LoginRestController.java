package com.pm.api;

import com.pm.dto.MemberDto;
import com.pm.dto.ProjectDto;
import com.pm.service.MemberService;
import com.pm.service.ProjectService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import com.pm.values.constValues;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Login REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-10
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-10          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api")
public class LoginRestController extends Controller {
    private final MemberService memberService;
    private final ProjectService projectService;
    public LoginRestController(MemberService memberService, ProjectService projectService) {
        this.memberService = memberService;
        this.projectService = projectService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseData save(@RequestParam(required = false, value = "account") String account,
                             @RequestParam(required = false, value = "password") String password,
                             HttpServletRequest request) {
        MemberDto result = memberService.getLogin(account, password);
        if(result == null) {
            return new ResponseData(false, "로그인에 실패했습니다.", null);
        }

        HttpSession session = getSessionData(request);
        List<ProjectDto> projectList = projectService.getMyProject(result.getIdx());

        if(result.getAdminYn() == 'Y') {
            session.setAttribute(constValues.PROJECT_SESSION, -1L);
        } else if(projectList.isEmpty()) {
            return new ResponseData(false, "로그인에 실패했습니다.", null);
        } else {
            session.setAttribute(constValues.PROJECT_SESSION, projectList.get(0).getIdx());
        }

        session.setAttribute(constValues.LOGIN_SESSION, memberService.getOneWithout(result.getIdx()));
        session.setAttribute(constValues.PROJECT_LIST_SESSION, projectList);

        return new ResponseData(true, "OK", null);
    }

    // 프로젝트 선택
    @PostMapping("/session_project")
    public ResponseData save(@RequestParam(required = false, value = "project") Long project,
                             HttpServletRequest request) {
        HttpSession session = getSessionData(request);
        session.setAttribute(constValues.PROJECT_SESSION, project);

        return new ResponseData(true, "OK", null);
    }
}
