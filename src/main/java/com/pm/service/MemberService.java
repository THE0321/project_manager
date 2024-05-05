package com.pm.service;

import com.pm.dto.MemberDto;
import com.pm.dto.TeamMemberDto;
import com.pm.entity.Member;
import com.pm.repository.MemberRepository;
import com.pm.repository.TeamMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Member Service
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
public class MemberService {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    public MemberService(MemberRepository memberRepository, TeamMemberRepository teamMemberRepository) {
        this.encoder = new BCryptPasswordEncoder();
        this.memberRepository = memberRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(String name, String account, Long positionIdx, Long roleIdx) {
        name = name == null ? "" : name;
        account = account == null ? "" : account;

        return memberRepository.countByNameAndAccountAndPositionIdxAndRoleIdx(name, account, positionIdx, roleIdx);
    }

    // 목록 조회(전체)
    @Transactional
    public List<MemberDto> getListAll(Long projectIdx, String name) {
        List<MemberDto> resultList = new ArrayList<>();
        memberRepository.findByAll(projectIdx, name).forEach(member -> {
            resultList.add(member.toDtoWithout());
        });

        return resultList;
    }

    // 목록 조회
    @Transactional
    public List<MemberDto> getList(String name, String account, Long positionIdx, Long roleIdx, Long page) {
        name = name == null ? "" : name;
        account = account == null ? "" : account;

        List<MemberDto> resultList = new ArrayList<>();
        memberRepository.findByNameAndAccountAndPositionIdxAndRoleIdxOrderByIdxDesc(name, account, positionIdx, roleIdx).forEach(member -> {
            resultList.add(member.toDtoWithout());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public MemberDto getOneWithout(Long idx) {
        return memberRepository.findById(idx).map(Member::toDtoWithout).orElse(null);
    }

    // 단건 조회
    @Transactional
    public MemberDto getOne(Long idx) {
        return memberRepository.findById(idx).map(Member::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(MemberDto memberDto) {
        if(memberDto.getPassword() != null && memberDto.getPassword().isEmpty()) {
            memberDto.setPassword(encoder.encode(memberDto.getPassword()));
        }

        return memberRepository.save(memberDto.toEntity()).getIdx();
    }

    // 내 팀 조회
    @Transactional
    public List<TeamMemberDto> getMyTeam(Long memberIdx) {
        List<TeamMemberDto> resultList = new ArrayList<>();
        teamMemberRepository.findByMemberIdx(memberIdx).forEach(teamMember -> {
            resultList.add(teamMember.toDto());
        });

        return resultList;
    }
}
