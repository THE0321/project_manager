package com.pm.api;

import com.pm.dto.TeamDto;
import com.pm.service.TeamService;
import com.pm.util.Controller;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Team REST Controller
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
@RequestMapping("/api/team")
public class TeamRestController extends Controller {
    private final TeamService teamService;
    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "name") String name,
                             @RequestParam(required = false, value = "member_list") Long[] memberList,
                             @RequestParam(required = false, value = "delete_list") Long[] deleteTeamList,
                             HttpServletRequest request) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "팀명을 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        TeamDto teamDto;
        if(idx == null) {
            teamDto = TeamDto.builder()
                    .name(name)
                    .register(loginIdx)
                    .build();
        } else {
            teamDto = teamService.getOne(idx);
            teamDto.setName(name);
        }

        Long result = teamService.saveItem(teamDto);

        // 팀 멤버 저장
        if(deleteTeamList != null && deleteTeamList.length > 0) {
            teamService.deleteTeamMember(deleteTeamList);
        }

        if(memberList != null && memberList.length > 0) {
            teamService.saveMember(result, memberList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }
    
    // 팀 목록 조회
    @GetMapping("/get_list")
    public ResponseData getList(@RequestParam(required = false, value = "name") String name,
                                HttpServletRequest request) {
        return new ResponseData(true, "조회했습니다.", teamService.getList(name));
    }
}
