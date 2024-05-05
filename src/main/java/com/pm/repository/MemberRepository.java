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
            "WHERE A.deleteYn = 'N' " +
            "AND UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "AND (:projectIdx IS NULL OR A.idx IN " +
            "(  SELECT DISTINCT IFNULL(B.memberIdx, C.memberIdx) " +
            "   FROM ProjectMember B " +
            "   LEFT JOIN TeamMember AS C ON B.teamIdx = C.teamIdx " +
            "   WHERE B.projectIdx = :projectIdx)) " +
            "ORDER BY A.name")
    List<Member> findByAll(Long projectIdx, String name);

    @Query("SELECT A " +
            "FROM Member A " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "AND UPPER(A.account) LIKE CONCAT('%', UPPER(:account), '%') " +
            "AND (:positionIdx IS NULL OR A.positionIdx = :positionIdx) " +
            "AND (:roleIdx IS NULL OR A.roleIdx = :roleIdx) " +
            "AND A.deleteYn = 'N' " +
            "ORDER BY A.idx DESC")
    List<Member> findByNameAndAccountAndPositionIdxAndRoleIdxOrderByIdxDesc(String name, String account, Long positionIdx, Long roleIdx);

    @Query("SELECT COUNT(A.idx) " +
            "FROM Member A " +
            "WHERE UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "AND UPPER(A.account) LIKE CONCAT('%', UPPER(:account), '%') " +
            "AND (:positionIdx IS NULL OR A.positionIdx = :positionIdx) " +
            "AND (:roleIdx IS NULL OR A.roleIdx = :roleIdx) " +
            "AND A.deleteYn = 'N'")
    Long countByNameAndAccountAndPositionIdxAndRoleIdx(String name, String account, Long positionIdx, Long roleIdx);

    @Query("SELECT A " +
            "FROM Member A " +
            "WHERE UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "OR UPPER(A.account) LIKE CONCAT('%', UPPER(:name), '%') " +
            "ORDER BY A.name")
    List<Member> findByNameOrderByName(String name);
}
