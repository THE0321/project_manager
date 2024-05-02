package com.pm.repository;

import com.pm.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * team JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-02
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-02          HTH             최초 등록
 **/
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT A " +
            "FROM Team A " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "ORDER BY A.idx DESC ")
    List<Team> findByNameContainingOrderByIdxDesc(String name);
}
