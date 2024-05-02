package com.pm.repository;

import com.pm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * role JPA Repository
 *
 * @author HTH
 * @version 1.0.0
 * @date 2024-05-01
 * ========================================================
 *  DATE                AUTHOR          NOTE
 * ========================================================
 *  2024-05-01          HTH             최초 등록
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT A " +
            "FROM Role A " +
            "LEFT JOIN FETCH A.registerMember " +
            "WHERE UPPER(A.name) LIKE CONCAT('%', UPPER(:name), '%') " +
            "ORDER BY A.idx DESC ")
    List<Role> findByNameContainingOrderByIdxDesc(String name);
}
