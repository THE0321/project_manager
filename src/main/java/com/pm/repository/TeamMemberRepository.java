package com.pm.repository;

import com.pm.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * team_member JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-03
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-03          HTH             최초 등록
 **/
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("SELECT A " +
            "FROM TeamMember A " +
            "LEFT JOIN FETCH A.team " +
            "WHERE A.memberIdx = :memberIdx")
    List<TeamMember> findByMemberIdx(Long memberIdx);
}
