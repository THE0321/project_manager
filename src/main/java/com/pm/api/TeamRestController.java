package com.pm.api;

import com.pm.dto.TeamDto;
import com.pm.service.TeamService;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class TeamRestController {
    private final TeamService teamService;
    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "name") String name,
                             HttpServletRequest request, Model model) {
        if(name == null || name.isEmpty()) {
            return new ResponseData(false, "팀명을 입력해주세요.", null);
        }

        TeamDto teamDto = null;
        if(idx == null) {
            teamDto = TeamDto.builder()
                    .name(name)
                    .register(1L)
                    .build();
        } else {
            teamDto = teamService.getOne(idx);
            teamDto.setName(name);
        }

        return new ResponseData(true, "저장되었습니다.", teamService.saveItem(teamDto));
    }
}
