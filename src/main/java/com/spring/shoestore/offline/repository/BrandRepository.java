package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query( value = """
    select * from Brand 
   where (_brand_code = ?1 or ?1 is null or ?1 like '')
   and (_brand_name = ?2 or ?2 is null or ?2 like '')
""",nativeQuery = true
    )
    Page<Brand> search(String brandCodeSearch, String brandNameSearch, Pageable pageable);
}