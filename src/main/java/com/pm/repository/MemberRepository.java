package com.pm.repository;

import com.pm.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * member JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT A " +
            "FROM Member A " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE (:name IS NULL OR A.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:account IS NULL OR A.account LIKE CONCAT('%', :account, '%')) " +
            "AND (:positionIdx IS NULL OR A.positionIdx = :positionIdx) " +
            "AND (:roleIdx IS NULL OR A.roleIdx = :roleIdx) " +
            "AND A.deleteYn = 'Y' " +
            "ORDER BY A.idx DESC")
    List<Member> findByNameAndAccountAndPositionIdxAndRoleIdxOrderByIdxDesc(String name, String account, Long positionIdx, Long roleIdx);

    @Query("SELECT COUNT(A.idx) " +
            "FROM Member A " +
            "WHERE (:name IS NULL OR A.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:account IS NULL OR A.account LIKE CONCAT('%', :account, '%')) " +
            "AND (:positionIdx IS NULL OR A.positionIdx = :positionIdx) " +
            "AND (:roleIdx IS NULL OR A.roleIdx = :roleIdx) " +
            "AND A.deleteYn = 'Y'")
    Long countByNameAndAccountAndPositionIdxAndRoleIdx(String name, String account, Long positionIdx, Long roleIdx);

    @Query("SELECT A " +
            "FROM Member A " +
            "WHERE (:name IS NULL OR A.name LIKE CONCAT('%', :name, '%') OR A.account LIKE CONCAT('%', :name, '%')) " +
            "ORDER BY A.name")
    List<Member> findByNameOrderByName(String name);
}
