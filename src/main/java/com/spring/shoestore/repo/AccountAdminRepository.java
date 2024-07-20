package com.spring.shoestore.repo;

import com.spring.shoestore.entity.AccountAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AccountAdminRepository extends JpaRepository<AccountAdmin, Integer> {

    @Query("SELECT a FROM AccountAdmin a WHERE a.userName LIKE %:userName%")
    Page<AccountAdmin> findByUserNameContaining(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT a FROM AccountAdmin a WHERE a.role = :role")
    Page<AccountAdmin> findByRole(@Param("role") String role, Pageable pageable);

    @Query("SELECT a FROM AccountAdmin a WHERE a.status = :status")
    Page<AccountAdmin> findByStatus(@Param("status") String status, Pageable pageable);

    @Query("SELECT a FROM AccountAdmin a")
    Page<AccountAdmin> findAll(Pageable pageable);
}