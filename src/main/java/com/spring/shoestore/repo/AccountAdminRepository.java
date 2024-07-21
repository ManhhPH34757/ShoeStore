package com.spring.shoestore.repo;

import com.spring.shoestore.entity.AccountAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountAdminRepository extends JpaRepository<AccountAdmin, Integer> {

    @Query("SELECT a FROM AccountAdmin a WHERE " +
            "(:userName IS NULL OR a.userName LIKE %:userName%) AND " +
            "(:role IS NULL OR a.role Like %:role%) AND " +
            "(:status IS NULL OR a.status Like %:status%)")
    List<AccountAdmin> filterByCriteria(@Param("userName") String userName,
                                        @Param("role") String role,
                                        @Param("status") String status);
}