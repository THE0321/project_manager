package com.pm.api;

import com.pm.dto.CalendarDto;
import com.pm.dto.ScheduleDto;
import com.pm.service.ScheduleService;
import com.pm.util.Controller;
import com.pm.util.DateFormat;
import com.pm.values.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * 
 * Schedule REST Controller
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@RestController
@RequestMapping("/api/schedule")
public class ScheduleRestController extends Controller {
    private final ScheduleService scheduleService;
    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 저장
    @PostMapping("/save")
    public ResponseData save(@RequestParam(required = false, value = "idx") Long idx,
                             @RequestParam(required = false, value = "title") String title,
                             @RequestParam(required = false, value = "schedule_date") String scheduleDateStr,
                             @RequestParam(required = false, value = "schedule_time") String scheduleTimeStr,
                             @RequestParam(required = false, value = "place") String place,
                             @RequestParam(required = false, value = "member_list") Long[] memberList,
                             @RequestParam(required = false, value = "delete_list") Long[] deleteMemberList,
                             HttpServletRequest request) {
        if(title == null || title.isEmpty()) {
            return new ResponseData(false, "일정명을 입력해주세요.", null);
        }

        if(scheduleDateStr == null || scheduleDateStr.isEmpty()) {
            return new ResponseData(false, "날짜를 선택해주세요.", null);
        }

        Long loginIdx = super.getLoginData(request).getIdx();

        DateFormat dateFormat = new DateFormat();
        Date scheduleDate = dateFormat.parseDate(scheduleDateStr);

        Time scheduleTime = null;
        if(scheduleTimeStr != null && !scheduleTimeStr.isEmpty()) {
            DateFormat timeFormat = new DateFormat("HH:mm");
            scheduleTime = timeFormat.parseTime(scheduleTimeStr);
        }

        ScheduleDto scheduleDto;
        if(idx == null) {
            scheduleDto = ScheduleDto.builder()
                    .title(title)
                    .scheduleDate(scheduleDate)
                    .scheduleTime(scheduleTime)
                    .place(place)
                    .register(loginIdx)
                    .build();
        } else {
            scheduleDto = scheduleService.getOne(idx);
            scheduleDto.setTitle(title);
            scheduleDto.setScheduleDate(scheduleDate);
            scheduleDto.setScheduleTime(scheduleTime);
            scheduleDto.setPlace(place);
            scheduleDto.setModifyDate(new Timestamp(System.currentTimeMillis()));
            scheduleDto.setModifier(loginIdx);
        }

        // 일정 저장
        Long result = scheduleService.saveItem(scheduleDto);

        // 일정 멤버 저장
        if(deleteMemberList != null && deleteMemberList.length > 0) {
            scheduleService.deleteMember(deleteMemberList);
        }

        if(memberList != null && memberList.length > 0) {
            scheduleService.saveMember(result, memberList, loginIdx);
        }

        return new ResponseData(true, "저장되었습니다.", result);
    }
    
    // 달력 조회
    @GetMapping("/get_calendar")
    public ResponseData getCalendar(HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        List<CalendarDto> result = scheduleService.getCalendars(loginIdx);
        return new ResponseData(true, "OK", result);
    }

    // 일정 목록 조회
    @GetMapping("/get_list")
    public ResponseData getList(@RequestParam(required = false, value = "date") String dateStr,
                                HttpServletRequest request) {
        Long loginIdx = super.getLoginData(request).getIdx();

        DateFormat dateFormat = new DateFormat();
        return new ResponseData(true, "OK", scheduleService.getList(dateFormat.parseDate(dateStr), loginIdx));
    }

    // 일정 상세 조회
    @GetMapping("/get_detail")
    public ResponseData getDetail(@RequestParam(required = false, value = "idx") Long idx,
                                  HttpServletRequest request) {
        ScheduleDto scheduleDto = scheduleService.getOne(idx);
        scheduleDto.setMemberList(scheduleService.getMemberList(idx));

        return new ResponseData(true, "OK", scheduleDto);
    }
}
