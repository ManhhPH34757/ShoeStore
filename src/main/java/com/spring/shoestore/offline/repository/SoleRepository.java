package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SoleRepository extends JpaRepository<Sole, Integer> {
    @Query(
            value = """
            SELECT *
                FROM Sole 
                WHERE (_sole_code = ?1 OR ?1 IS NULL OR ?1 LIKE '')
                AND (_sole_name = ?2 OR ?2 IS NULL OR ?2 LIKE '')
    
""",nativeQuery = true
    )
    Page<Sole> search(String soleCode, String soleName, Pageable pageable);
}