package com.pm.api;

import com.pm.dto.MemberDto;
import com.pm.service.MemberService;
import com.pm.service.TeamService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 *
 * Member REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/member")
public class MemberRestController extends Controller {
    private final MemberService memberService;
    private final TeamService teamService;
    public MemberRestController(MemberService memberService, TeamService teamService) {
        this.memberService = memberService;
        this.teamService = teamService;
    }
    
    // 유저 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "name") String name,
                             @RequestParam(required = false, value = "account") String account,
                             @RequestParam(required = false, value = "password") String password,
                             @RequestParam(required = false, value = "position_idx") Long positionIdx,
                             @RequestParam(required = false, value = "role_idx") Long roleIdx,
                             @RequestParam(required = false, value = "disable_yn") Character disableYn,
                             @RequestParam(required = false, value = "admin_yn") Character adminYn,
                             @RequestParam(required = false, value = "team_list") Long[] teamList,
                             @RequestParam(required = false, value = "delete_list") Long[] deleteTeamList,
                             @RequestParam(required = false, value = "note") String note,
                             HttpServletRequest request) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "이름을 입력해주세요.", null);
        }

        if(account == null || account.isEmpty()) {
            return new ResponseData(false, "계정을 입력해주세요.", null);
        }

        if(idx == null && (password == null || password.isEmpty())) {
            return new ResponseData(false, "비밀번호를 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        MemberDto memberDto;
        if(idx == null) {
            memberDto = MemberDto.builder()
                    .name(name)
                    .account(account)
                    .password(password)
                    .positionIdx(positionIdx)
                    .roleIdx(roleIdx)
                    .disableYn(disableYn)
                    .adminYn(adminYn)
                    .note(note)
                    .register(loginIdx)
                    .build();
        } else {
            memberDto = memberService.getOne(idx);
            memberDto.setName(name);
            memberDto.setAccount(account);
            memberDto.setPassword(password);
            memberDto.setPositionIdx(positionIdx);
            memberDto.setRoleIdx(roleIdx);
            memberDto.setDisableYn(disableYn);
            memberDto.setAdminYn(adminYn);
            memberDto.setNote(note);
            memberDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            memberDto.setModifier(loginIdx);
        }

        Long result = memberService.saveItem(memberDto);
        
        // 팀 멤버 저장
        if(deleteTeamList != null && deleteTeamList.length > 0) {
            teamService.deleteTeamMember(deleteTeamList);
        }
        
        if(teamList != null && teamList.length > 0) {
            teamService.saveTeam(result, teamList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 직급 저장
    @PostMapping("/change_position")
    public ResponseData changePosition(@RequestParam(required = false, value = "idx") Long idx,
                                       @RequestParam(required = false, value = "position_idx") Long positionIdx,
                                       HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setPositionIdx(positionIdx);
        memberDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        memberDto.setModifier(loginIdx);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 역할 저장
    @PostMapping("/change_role")
    public ResponseData changeRole(@RequestParam(required = false, value = "idx") Long idx,
                                   @RequestParam(required = false, value = "role_idx") Long roleIdx,
                                   HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setRoleIdx(roleIdx);
        memberDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        memberDto.setModifier(loginIdx);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 활성화 여부 저장
    @PostMapping("/change_disable_yn")
    public ResponseData changeDisableYn(@RequestParam(required = false, value = "idx") Long idx,
                                        @RequestParam(required = false, value = "disable_yn") Character disableYn,
                                        HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setDisableYn(disableYn);
        memberDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        memberDto.setModifier(loginIdx);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 멤버 목록 조회
    @GetMapping("/get_list")
    public ResponseData getList(@RequestParam(required = false, value = "project_idx") Long projectIdx,
                                @RequestParam(required = false, value = "name") String name,
                                HttpServletRequest request) {
        projectIdx = projectIdx == null ? super.getProjectData(request) : projectIdx;
        return new ResponseData(true, "조회했습니다.", memberService.getListAll(projectIdx, name));
    }
}
