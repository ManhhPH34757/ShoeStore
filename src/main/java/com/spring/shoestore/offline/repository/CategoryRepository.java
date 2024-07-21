package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query( value = """
    select * from Category
   where (_category_code = ?1 or ?1 is null or ?1 like '')
   and (_category_name = ?2 or ?2 is null or ?2 like '')
""",nativeQuery = true
    )
    Page<Category> search(String categoryCodeSearch, String categoryNameSearch, Pageable pageable);
}