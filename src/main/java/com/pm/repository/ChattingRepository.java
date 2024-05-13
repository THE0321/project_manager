package com.pm.repository;

import com.pm.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * chatting JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-13
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-13          HTH             최초 등록
 **/
public interface ChattingRepository extends JpaRepository<Chatting, Long> {
    @Query("SELECT A " +
            "FROM Chatting A " +
            "LEFT JOIN FETCH A.member " +
            "WHERE A.chattingRoomIdx = :chattingRoomIdx " +
            "ORDER BY A.idx DESC " +
            "LIMIT :per OFFSET :offset")
    List<Chatting> findChatting(Long chattingRoomIdx, Integer per, Long offset);
}
