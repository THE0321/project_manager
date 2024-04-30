package com.pm.service;

import com.pm.dto.ProjectDto;
import com.pm.dto.ProjectMemberDto;
import com.pm.dto.ProjectStatusDto;
import com.pm.entity.Project;
import com.pm.entity.ProjectStatus;
import com.pm.repository.ProjectMemberRepository;
import com.pm.repository.ProjectRepository;
import com.pm.repository.ProjectStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Long getCount(String title, Long statusIdx, String startDate, String endDate) {
        return projectRepository.countByTitleAndStatusIdxAndStartDateAndEndDate(title, statusIdx, startDate, endDate);
    }

    // 목록 조회
    @Transactional
    public List<ProjectDto> getList(String title, Long statusIdx, String startDate, String endDate, Long page) {
        List<Project> projectList = projectRepository.findByTitleAndStatusIdxAndStartDateAndEndDateOrderByIdxDesc(title, statusIdx, startDate, endDate);
        List<ProjectDto> resultList = new ArrayList<>();

        for (Project project : projectList) {
            resultList.add(project.toDto());
        }

        return resultList;
    }

    // 단건 조회
    @Transactional
    public ProjectDto getOne(Long idx) {
        Optional<Project> wrapper = projectRepository.findById(idx);
        return wrapper.map(Project::toDto).orElse(null);
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

    // 담당자 추가
    @Transactional
    public void saveMember(List<ProjectMemberDto> projectMemberDtoList) {
        for (ProjectMemberDto projectMemberDto : projectMemberDtoList) {
            projectMemberRepository.save(projectMemberDto.toEntity());
        }
    }

    // 담당자 삭제
    @Transactional
    public void deleteMember(List<Long> idxList) {
        for (Long idx : idxList) {
            projectMemberRepository.deleteById(idx);
        }
    }

    // 내 프로젝트 조회
    @Transactional
    public List<ProjectDto> getMyProject(Long memberIdx) {
        // TODO: 팀 기준 조회 추가 필요
        List<Project> projectList = projectRepository.findByMemberIdxOrderByTitle(memberIdx);
        List<ProjectDto> resultList = new ArrayList<>();

        for (Project project : projectList) {
            resultList.add(project.toDto());
        }

        return resultList;
    }

    // 상태 조회
    @Transactional
    public List<ProjectStatusDto> getStatus() {
        List<ProjectStatus> statusList = projectStatusRepository.findAll();
        List<ProjectStatusDto> resultList = new ArrayList<>();

        for (ProjectStatus status : statusList) {
            resultList.add(status.toDto());
        }

        return resultList;
    }

}
