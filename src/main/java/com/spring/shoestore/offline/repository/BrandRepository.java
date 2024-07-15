package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(
            value = """
SELECT *
	FROM Brand
	WHERE (_brand_code = ?1 OR ?1 IS NULL OR ?1 LIKE '')
	AND (_brand_name = ?2 OR ?2 IS NULL OR ?2 LIKE '')
    
""",nativeQuery = true
    )
    Page<Brand> search(String brandCode, String brandName, Pageable pageable);
}