package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.entity.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SoleRepository extends JpaRepository<Sole, Integer> {
    @Query( value = """
    select * from Sole 
   where (_sole_code = ?1 or ?1 is null or ?1 like '')
   and (_sole_name = ?2 or ?2 is null or ?2 like '')
""",nativeQuery = true
    )
    Page<Sole> search(String soleCodeSearch, String soleNameSearch, Pageable pageable);
}