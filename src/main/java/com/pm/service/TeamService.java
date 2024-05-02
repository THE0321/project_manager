package com.pm.service;

import com.pm.dto.TeamDto;
import com.pm.entity.Team;
import com.pm.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Team Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // 목록 조회
    @Transactional
    public List<TeamDto> getList(String name) {
        name = name == null ? "" : name;

        List<TeamDto> resultList = new ArrayList<>();
        teamRepository.findByNameContainingOrderByIdxDesc(name).forEach(team -> {
            resultList.add(team.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public TeamDto getOne(Long idx) {
        return teamRepository.findById(idx).map(Team::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(TeamDto teamDto) {
        return teamRepository.save(teamDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        teamRepository.deleteById(idx);
    }
}
