package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDtoRepository extends JpaRepository<ProductDto, Integer> {

    @Query( value = """
       SELECT dbo.product._id_product AS 'id',dbo.product._product_code AS 'product_code',
       dbo.product._product_name AS 'product_name',dbo.brand._brand_name AS 'brand_name',\s
       dbo.category._category_name AS 'category_name',dbo.sole._sole_name AS 'sole_name',dbo.material._material_name AS 'material_name',
       COALESCE(SUM(product_details._quantity),0) AS 'quantity'
       FROM dbo.product LEFT JOIN dbo.product_details ON product_details._id_product = product._id_product
       JOIN dbo.brand ON brand._id_brand = product._id_brand
       JOIN dbo.category ON category._id_category = product._id_category
       JOIN dbo.sole ON sole._id_sole = product._id_sole
       JOIN dbo.material ON material._id_material = product._id_material
       GROUP BY dbo.product._id_product,dbo.product._product_code,dbo.product._product_name,
       dbo.brand._brand_name, dbo.category._category_name,dbo.sole._sole_name,dbo.material._material_name
       ORDER BY dbo.product._id_product DESC
       
""", nativeQuery = true
    )
    Page<ProductDto> findAllQuantity(Pageable pageable);


     @Query(
            value = """
            SELECT dbo.product._id_product AS 'id',dbo.product._product_code AS 'product_code',
                    dbo.product._product_name AS 'product_name',dbo.brand._brand_name AS 'brand_name',
                    dbo.category._category_name AS 'category_name',dbo.sole._sole_name AS 'sole_name',dbo.material._material_name AS 'material_name',
                    COALESCE(SUM(product_details._quantity),0) AS 'quantity'
                    FROM dbo.product LEFT JOIN dbo.product_details ON product_details._id_product = product._id_product
                    JOIN dbo.brand ON brand._id_brand = product._id_brand
                    JOIN dbo.category ON category._id_category = product._id_category
                    JOIN dbo.sole ON sole._id_sole = product._id_sole
                    JOIN dbo.material ON material._id_material = product._id_material
                    WHERE (dbo.product._product_name = ?1 OR ?1 IS NULL OR ?1 = '')
                    AND (dbo.product._id_brand = ?2 OR ?2 IS NULL  )
                    AND (dbo.product._id_category = ?3 OR ?3 IS NULL)
                    AND (dbo.product._id_sole = ?4 OR ?4 IS NULL)
                    AND (dbo.product._id_material = ?5 OR ?5 IS NULL)
                    GROUP BY dbo.product._id_product,dbo.product._product_code,dbo.product._product_name,
                    dbo.brand._brand_name, dbo.category._category_name,dbo.sole._sole_name,dbo.material._material_name
                    ORDER BY dbo.product._id_product DESC     
            """,nativeQuery = true
    )
    Page<ProductDto> search(String product_name, Integer brand_id,Integer category_id,Integer sole_id,Integer material_id,Pageable pageable);


    @Query( value = """
       SELECT dbo.product._id_product AS 'id',dbo.product._product_code AS 'product_code',
       dbo.product._product_name AS 'product_name',dbo.brand._brand_name AS 'brand_name',\s
       dbo.category._category_name AS 'category_name',dbo.sole._sole_name AS 'sole_name',dbo.material._material_name AS 'material_name',
       COALESCE(SUM(product_details._quantity),0) AS 'quantity'
       FROM dbo.product LEFT JOIN dbo.product_details ON product_details._id_product = product._id_product
       JOIN dbo.brand ON brand._id_brand = product._id_brand
       JOIN dbo.category ON category._id_category = product._id_category
       JOIN dbo.sole ON sole._id_sole = product._id_sole
       JOIN dbo.material ON material._id_material = product._id_material
       GROUP BY dbo.product._id_product,dbo.product._product_code,dbo.product._product_name,
       dbo.brand._brand_name, dbo.category._category_name,dbo.sole._sole_name,dbo.material._material_name
       ORDER BY dbo.product._id_product DESC
       
""", nativeQuery = true
    )
    List<ProductDto> findAllQuantityXuatFile();
}