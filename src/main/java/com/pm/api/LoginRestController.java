package com.pm.api;

import com.pm.dto.MemberDto;
import com.pm.dto.ProjectDto;
import com.pm.service.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
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
    private final FileService fileService;

    public LoginRestController(MemberService memberService, ProjectService projectService, FileService fileService) {
        this.memberService = memberService;
        this.projectService = projectService;
        this.fileService = fileService;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseData login(@RequestParam(required = false, value = "account") String account,
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

    // 로그아웃
    @PostMapping("/logout")
    public ResponseData logout(HttpServletRequest request) {
        HttpSession session = getSessionData(request);

        session.removeAttribute(constValues.LOGIN_SESSION);
        session.removeAttribute(constValues.PROJECT_SESSION);
        session.removeAttribute(constValues.PROJECT_LIST_SESSION);

        return new ResponseData(true, "OK", null);
    }

    // 프로젝트 선택
    @PostMapping("/session_project")
    public ResponseData saveSessionProject(@RequestParam(required = false, value = "project") Long project,
                                           HttpServletRequest request) {
        HttpSession session = getSessionData(request);
        session.setAttribute(constValues.PROJECT_SESSION, project);

        return new ResponseData(true, "OK", null);
    }

    // 내 정보 수정
    @PostMapping("/save_info")
    public ResponseData saveInfo(@RequestParam(required = false, value = "name") String name,
                                 @RequestParam(required = false, value = "password") String password,
                                 @RequestParam(required = false, value = "upload_file") MultipartFile uploadFile,
                                 HttpServletRequest request) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "내용을 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        MemberDto memberDto = memberService.getOne(loginIdx);
        memberDto.setName(name);
        memberDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        memberDto.setModifier(loginIdx);

        if(password != null && !password.isEmpty()) {
            memberDto.setPassword(password);
        }

        if(uploadFile != null) {
            try {
                memberDto.setProfileIdx(fileService.saveItem(uploadFile, loginIdx));
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }

        memberService.saveItem(memberDto);

        HttpSession session = getSessionData(request);
        session.setAttribute(constValues.LOGIN_SESSION, memberService.getOneWithout(loginIdx));

        return new ResponseData(true, "OK", null);
    }
}
