package com.pm.service;

import com.pm.repository.ScheduleMemberRepository;
import com.pm.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

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

    // TODO: 달력 조회
    
    // TODO: 목록 조회
    
    // TODO: 단건 조회
    
    // TODO: 생성/수정
    
    // TODO: 삭제
    
    // TODO: 공유 멤버 목록
    
    // TODO: 공유 멤버 추가
    
    // TODO: 공유 멤버 삭제
}
