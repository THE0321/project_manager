package com.pm.repository;

import com.pm.entity.ChattingMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * chatting_member JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-13
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-13          HTH             최초 등록
 **/
public interface ChattingMemberRepository extends JpaRepository<ChattingMember, Long> {
    @Query("SELECT A " +
            "FROM ChattingMember A " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.chattingRoomIdx = :chattingRoomIdx")
    List<ChattingMember> findByChattingRoomIdx(Long chattingRoomIdx);
}
