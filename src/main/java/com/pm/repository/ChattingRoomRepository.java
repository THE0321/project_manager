package com.pm.repository;

import com.pm.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
 * chatting_room JPA Repository
 * 
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-13
 * ========================================================
 *  DATE                AUTHOR          NOTE 
 * ========================================================
 *  2024-05-13          HTH             최초 등록
 **/
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    @Query("SELECT A " +
            "FROM ChattingRoom A " +
            "WHERE A.register = :memberIdx " +
            "OR A.idx IN ( " +
            "    SELECT B.chattingRoomIdx " +
            "    FROM ChattingMember B " +
            "    WHERE B.chattingRoomIdx = :memberIdx " +
            ") " +
            "ORDER BY A.idx DESC ")
    List<ChattingRoom> findByMemberIdx(Long memberIdx);

    boolean existsByCode(String code);

    ChattingRoom findByCode(String code);
}
