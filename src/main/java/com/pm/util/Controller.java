package com.pm.util;

import com.pm.dto.MemberDto;
import com.pm.dto.ProjectDto;
import com.pm.values.constValues;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

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
 *  2024-05-10          HTH             개발 완료
 **/
public class Controller {
    // 공통 변수 세팅
    public Model setModel(HttpServletRequest request, Model model) {
        HttpSession session = getSessionData(request);

        model.addAttribute("session_info", getLoginData(session));
        model.addAttribute("session_project", getProjectData(session));
        model.addAttribute("session_project_list", getProjectListData(session));

        String[] url = request.getRequestURI().split("/");
        model.addAttribute("current_page", url.length > 1 ? url[1] : "");

        return model;
    }

    // 세션 데이터 획득
    public HttpSession getSessionData(HttpServletRequest request) {
        return request.getSession(true);
    }

    // 로그인 데이터 획득
    public MemberDto getLoginData(HttpServletRequest request) {
        HttpSession session = this.getSessionData(request);
        return (MemberDto) session.getAttribute(constValues.LOGIN_SESSION);
    }

    public MemberDto getLoginData(HttpSession session) {
        return (MemberDto) session.getAttribute(constValues.LOGIN_SESSION);
    }

    // 프로젝트 데이터 획득
    public Long getProjectData(HttpServletRequest request) {
        HttpSession session = this.getSessionData(request);
        return (Long) session.getAttribute(constValues.PROJECT_SESSION);
    }

    public Long getProjectData(HttpSession session) {
        return (Long) session.getAttribute(constValues.PROJECT_SESSION);
    }

    // 프로젝트 목록 획득
    public List<ProjectDto> getProjectList(HttpServletRequest request) {
        HttpSession session = this.getSessionData(request);
        return (List<ProjectDto>) session.getAttribute(constValues.PROJECT_LIST_SESSION);
    }

    public List<ProjectDto> getProjectListData(HttpSession session) {
        return (List<ProjectDto>) session.getAttribute(constValues.PROJECT_LIST_SESSION);
    }
}
