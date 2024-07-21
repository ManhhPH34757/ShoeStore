package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query( value = """
    select * from Material 
   where (_material_code = ?1 or ?1 is null or ?1 like '')
   and (_material_name = ?2 or ?2 is null or ?2 like '')
""",nativeQuery = true
    )
    Page<Material> search(String materialCodeSearch, String materialNameSearch, Pageable pageable);
}