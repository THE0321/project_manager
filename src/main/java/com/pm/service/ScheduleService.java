package com.pm.service;

import com.pm.dto.CalendarDto;
import com.pm.dto.ScheduleDto;
import com.pm.dto.ScheduleMemberDto;
import com.pm.entity.Schedule;
import com.pm.repository.ScheduleMemberRepository;
import com.pm.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Schedule Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-09
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-09          HTH             최초 등록
 **/
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMemberRepository scheduleMemberRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMemberRepository = scheduleMemberRepository;
    }

    // 달력 조회
    public List<CalendarDto> getCalendars(Long memberIdx) {
        List<CalendarDto> resultList = new ArrayList<>();
        scheduleRepository.getCalendarList().forEach(calendar -> {
            resultList.add(CalendarDto.builder().year(calendar.getYear()).month(calendar.getMonth()).day(calendar.getDay()).build());
        });

        return resultList;
    }

    // 목록 조회
    public List<ScheduleDto> getList(Date scheduleDate, Long memberIdx) {
        List<ScheduleDto> resultList = new ArrayList<>();
        scheduleRepository.findByMemberIdx(scheduleDate, memberIdx).forEach(schedule -> {
            resultList.add(schedule.toDto());
        });

        return resultList;
    }
    
    // 단건 조회
    public ScheduleDto getOne(Long idx) {
        return scheduleRepository.findById(idx).map(Schedule::toDto).orElse(null);
    }
    
    // 생성/수정
    public Long saveItem(ScheduleDto scheduleDto) {
        return scheduleRepository.save(scheduleDto.toEntity()).getIdx();
    }
    
    // 삭제
    public void deleteItem(Long idx) {
        scheduleRepository.deleteById(idx);
    }
    
    // 공유 멤버 목록
    public List<ScheduleMemberDto> getMemberList(Long scheduleIdx) {
        List<ScheduleMemberDto> resultList = new ArrayList<>();
        scheduleMemberRepository.findByScheduleIdx(scheduleIdx).forEach(scheduleMember -> {
            resultList.add(scheduleMember.toEntity());
        });

        return resultList;
    }
    
    // 공유 멤버 추가
    public void saveMember(Long scheduleIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(memberIdx -> {
            scheduleMemberRepository.save(ScheduleMemberDto.builder().scheduleIdx(scheduleIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }
    
    // 공유 멤버 삭제
    public void deleteMember(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(scheduleMemberRepository::deleteById);
    }
}
