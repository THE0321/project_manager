package com.pm.api;

import com.pm.dto.ProjectDto;
import com.pm.service.ProjectService;
import com.pm.util.Controller;
import com.pm.util.DateFormat;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 *
 * Project REST Controller
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/project")
public class ProjectRestController extends Controller {
    private final ProjectService projectService;
    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // 프로젝트 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "description") String description,
                             @RequestParam(required = false, value = "status_idx") Long statusIdx,
                             @RequestParam(required = false, value = "start_date") String startDate,
                             @RequestParam(required = false, value = "end_date") String endDate,
                             @RequestParam(required = false, value = "member_list") Long[] memberList,
                             @RequestParam(required = false, value = "team_list") Long[] teamList,
                             @RequestParam(required = false, value = "delete_list") Long[] deleteMemberList,
                             HttpServletRequest request) {
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "프로젝트명을 입력해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        DateFormat dateFormat = new DateFormat();
        ProjectDto projectDto;
        if(idx == null) {
            projectDto = ProjectDto.builder()
                    .title(title)
                    .description(description)
                    .statusIdx(statusIdx)
                    .startDate(startDate == null || startDate.isEmpty() ? null : dateFormat.parseDate(startDate))
                    .endDate(endDate == null || endDate.isEmpty() ? null : dateFormat.parseDate(endDate))
                    .register(loginIdx)
                    .build();
        } else {
            projectDto = projectService.getOne(idx);
            projectDto.setTitle(title);
            projectDto.setDescription(description);
            projectDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            projectDto.setModifier(loginIdx);
            projectDto.setStartDate(startDate == null || startDate.isEmpty() ? null : dateFormat.parseDate(startDate));
            projectDto.setEndDate(endDate == null || endDate.isEmpty() ? null : dateFormat.parseDate(endDate));

            if(projectDto.getStatusIdx() == null) {
                if(statusIdx != null) {
                    projectDto.setStatusIdx(statusIdx);
                    projectDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
                }
            } else if(!projectDto.getStatusIdx().equals(statusIdx)) {
                projectDto.setStatusIdx(statusIdx);
                projectDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        }

        // 프로젝트 저장
        Long result = projectService.saveItem(projectDto);
        
        // 프로젝트 담당자 저장
        if(deleteMemberList != null && deleteMemberList.length > 0) {
            projectService.deleteMember(deleteMemberList);
        }

        if(teamList != null && teamList.length > 0) {
            projectService.saveTeam(result, teamList, loginIdx);
        }

        if(memberList != null && memberList.length > 0) {
            projectService.saveMember(result, memberList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }

    // 프로젝트 상태 변경
    @PostMapping("/change_status")
    public ResponseData changeStatus(@RequestParam(required = false) Long idx,
                                     @RequestParam(required = false, value = "status_idx") Long statusIdx,
                                     HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        ProjectDto projectDto = projectService.getOne(idx);
        projectDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
        projectDto.setModifier(loginIdx);

        if(projectDto.getStatusIdx() == null) {
            if(statusIdx != null) {
                projectDto.setStatusIdx(statusIdx);
                projectDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
            }
        } else if(!projectDto.getStatusIdx().equals(statusIdx)) {
            projectDto.setStatusIdx(statusIdx);
            projectDto.setStatusDate(new Timestamp(System.currentTimeMillis()));
        }

        projectService.saveItem(projectDto);

        return new ResponseData(true, "저장되었습니다.", null);
    }
}
