package com.pm.api;

import com.pm.dto.MemberDto;
import com.pm.service.MemberService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class MemberRestController {
    private final MemberService memberService;
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
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
                             @RequestParam(required = false, value = "note") String note,
                             HttpServletRequest request, Model model) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "이름을 입력해주세요.", null);
        }

        if(account == null || account.isEmpty()) {
            return new ResponseData(false, "계정을 입력해주세요.", null);
        }

        if(password == null || password.isEmpty()) {
            return new ResponseData(false, "비밀번호를 입력해주세요.", null);
        }

        MemberDto memberDto = null;
        if(idx == null) {
            memberDto = MemberDto.builder()
                    .name(name)
                    .account(account)
                    .password(password)
                    .positionIdx(positionIdx)
                    .roleIdx(roleIdx)
                    .disableYn(disableYn)
                    .note(note)
                    .register(1L)
                    .build();
        } else {
            memberDto = memberService.getOne(idx);
            memberDto.setName(name);
            memberDto.setAccount(account);
            memberDto.setPassword(password);
            memberDto.setPositionIdx(positionIdx);
            memberDto.setRoleIdx(roleIdx);
            memberDto.setDisableYn(disableYn);
            memberDto.setNote(note);
            memberDto.setModifier(1L);
        }

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 직급 저장
    @PostMapping("/change_position")
    public ResponseData changePosition(@RequestParam(required = false, value = "idx") Long idx,
                                       @RequestParam(required = false, value = "position_idx") Long positionIdx,
                                       HttpServletRequest request, Model model) {
        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setPositionIdx(positionIdx);
        memberDto.setModifier(1L);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 역할 저장
    @PostMapping("/change_role")
    public ResponseData changeRole(@RequestParam(required = false, value = "idx") Long idx,
                                   @RequestParam(required = false, value = "role_idx") Long roleIdx,
                                   HttpServletRequest request, Model model) {
        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setRoleIdx(roleIdx);
        memberDto.setModifier(1L);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }

    // 활성화 여부 저장
    @PostMapping("/change_disable_yn")
    public ResponseData changeDisableYn(@RequestParam(required = false, value = "idx") Long idx,
                                        @RequestParam(required = false, value = "disable_yn") Character disableYn,
                                        HttpServletRequest request, Model model) {
        MemberDto memberDto = memberService.getOne(idx);
        memberDto.setDisableYn(disableYn);
        memberDto.setModifier(1L);

        return new ResponseData(true, "저장되었습니다.", memberService.saveItem(memberDto));
    }
}
