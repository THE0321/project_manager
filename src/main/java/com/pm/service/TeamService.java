package com.pm.service;

import com.pm.dto.TeamDto;
import com.pm.dto.TeamMemberDto;
import com.pm.entity.Team;
import com.pm.repository.TeamMemberRepository;
import com.pm.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final TeamMemberRepository teamMemberRepository;
    public TeamService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    // 목록 조회
    @Transactional
    public List<TeamDto> getList(String name) {
        name = name == null ? "" : name;

        List<TeamDto> resultList = new ArrayList<>();
        teamRepository.findByNameContainingOrderByName(name).forEach(team -> {
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

    // 팀 멤버 조회
    @Transactional
    public List<TeamMemberDto> getTeamMember(Long teamIdx) {
        List<TeamMemberDto> resultList = new ArrayList<>();
        teamMemberRepository.findById(teamIdx).ifPresent(teamMember -> {
            resultList.add(teamMember.toDto());
        });

        return resultList;
    }

    // 팀 멤버 추가
    @Transactional
    public void saveTeam(Long memberIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(teamIdx -> {
            teamMemberRepository.save(TeamMemberDto.builder().teamIdx(teamIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }

    @Transactional
    public void saveMember(Long teamIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(memberIdx -> {
            teamMemberRepository.save(TeamMemberDto.builder().teamIdx(teamIdx).memberIdx(memberIdx).register(register).build().toEntity());
        });
    }

    // 팀 멤버 제거
    @Transactional
    public void deleteTeamMember(Long[] idxArray) {
        Arrays.asList(idxArray).forEach(teamMemberRepository::deleteById);
    }
}
