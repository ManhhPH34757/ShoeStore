package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query(
            value = """
        SELECT *
	FROM Material 
	WHERE (_material_code = ?1 OR ?1 IS NULL OR ?1 LIKE '')
	AND (_material_name = ?2 OR ?2 IS NULL OR ?2 LIKE '')
""",nativeQuery = true
    )
    Page<Material> search(String materialCode, String materialName, Pageable pageable);
}