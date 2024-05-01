package com.pm.service;

import com.pm.dto.MemberDto;
import com.pm.entity.Member;
import com.pm.repository.MemberRepository;
import jakarta.transaction.Transactional;
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
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 갯수 조회
    @Transactional
    public Long getCount(String name, String account, Long positionIdx, Long roleIdx) {
        return memberRepository.countByNameAndAccountAndPositionIdxAndRoleIdx(name, account, positionIdx, roleIdx);
    }

    // 목록 조회
    @Transactional
    public List<MemberDto> getList(String name, String account, Long positionIdx, Long roleIdx, Long page) {
        List<MemberDto> resultList = new ArrayList<>();
        memberRepository.findByNameAndAccountAndPositionIdxAndRoleIdxOrderByIdxDesc(name, account, positionIdx, roleIdx).forEach(member -> {
            resultList.add(member.toDto());
        });

        return resultList;
    }

    // 단건 조회
    @Transactional
    public MemberDto getOne(Long idx) {
        return memberRepository.findById(idx).map(Member::toDto).orElse(null);
    }

    // 생성/수정
    @Transactional
    public Long saveItem(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity()).getIdx();
    }
}
