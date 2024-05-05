package com.pm.service;

import com.pm.dto.ProjectDto;
import com.pm.dto.ProjectMemberDto;
import com.pm.dto.ProjectStatusDto;
import com.pm.entity.Project;
import com.pm.entity.ProjectMember;
import com.pm.repository.ProjectMemberRepository;
import com.pm.repository.ProjectRepository;
import com.pm.repository.ProjectStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Project Service
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-04-30
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-04-30          HTH             최초 등록
 **/
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectMemberRepository projectMemberRepository;
    public ProjectService(ProjectRepository projectRepository, ProjectStatusRepository projectStatusRepository, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.projectStatusRepository = projectStatusRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(String title, Long statusIdx, Date startDate, Date endDate) {
        title = title == null ? "" : title;

        return projectRepository.countByTitleAndStatusIdxAndStartDateAndEndDate(title, statusIdx, startDate, endDate);
    }

    // 목록 조회
    @Transactional
    public List<ProjectDto> getList(String title, Long statusIdx, Date startDate, Date endDate, Long page) {
        title = title == null ? "" : title;

        List<ProjectDto> resultList = new ArrayList<>();
        projectRepository.findByTitleAndStatusIdxAndStartDateAndEndDateOrderByIdxDesc(title, statusIdx, startDate, endDate).forEach(project -> {
            resultList.add(project.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public ProjectDto getOne(Long idx) {
        return projectRepository.findById(idx).map(Project::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(ProjectDto projectDto) {
        return projectRepository.save(projectDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        projectRepository.deleteById(idx);
    }

    // 담당자 조회
    @Transactional
    public List<ProjectMemberDto> getMemberList(Long projectIdx) {
        List<ProjectMemberDto> resultList = new ArrayList<>();
        projectMemberRepository.findByProjectIdxOrderByIdxDesc(projectIdx).forEach(projectMember -> {
            resultList.add(projectMember.toDto());
        });

        return resultList;
    }

    // 팀 추가
    @Transactional
    public void saveTeam(Long projectIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(idx -> {
            projectMemberRepository.save(ProjectMemberDto.builder().projectIdx(projectIdx).teamIdx(idx).register(register).build().toEntity());
        });
    }

    // 담당자 추가
    @Transactional
    public void saveMember(Long projectIdx, Long[] idxArray, Long register) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(idx -> {
            projectMemberRepository.save(ProjectMemberDto.builder().projectIdx(projectIdx).memberIdx(idx).register(register).build().toEntity());
        });
    }


    // 팀/담당자 삭제
    @Transactional
    public void deleteMember(Long[] idxArray) {
        List<Long> idxList = Arrays.asList(idxArray);
        idxList.forEach(projectMemberRepository::deleteById);
    }

    // 내 프로젝트 조회
    @Transactional
    public List<ProjectDto> getMyProject(Long memberIdx) {
        List<ProjectDto> resultList = new ArrayList<>();

        // TODO: 팀 기준 조회 추가 필요
        projectRepository.findByMemberIdxOrderByTitle(memberIdx).forEach(project -> {
            resultList.add(project.toDto());
        });

        return resultList;
    }

    // 상태 조회
    @Transactional
    public List<ProjectStatusDto> getStatus() {
        List<ProjectStatusDto> resultList = new ArrayList<>();
        projectStatusRepository.findAll().forEach(status -> {
            resultList.add(status.toDto());
        });

        return resultList;
    }
}