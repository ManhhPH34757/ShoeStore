package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(
            value = """
SELECT *
	FROM Category 
	WHERE (_category_code = ?1 OR ?1 IS NULL OR ?1 LIKE '')
	AND (_category_name = ?2 OR ?2 IS NULL OR ?2 LIKE '')
    
""",nativeQuery = true
    )
    Page<Category> search(String categoryCode, String categoryName, Pageable pageable);
}