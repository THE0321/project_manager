package com.pm.service;

import com.pm.dto.RoleDto;
import com.pm.entity.Role;
import com.pm.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Role Service
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // 목록 조회
    @Transactional
    public List<RoleDto> getList(String name) {
        name = name == null ? "" : name;

        List<RoleDto> resultList = new ArrayList<>();
        roleRepository.findByNameContainingOrderByIdxDesc(name).forEach(role -> {
            resultList.add(role.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public RoleDto getOne(Long idx) {
        return roleRepository.findById(idx).map(Role::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(RoleDto roleDto) {
        return roleRepository.save(roleDto.toEntity()).getIdx();
    }

    // 삭제
    @Transactional
    public void deleteItem(Long idx) {
        roleRepository.deleteById(idx);
    }
}
