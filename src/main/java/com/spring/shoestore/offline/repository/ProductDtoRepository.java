package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDtoRepository extends JpaRepository<ProductDto, Integer> {
  @Query(value = """
          select product._id_product as 'id' , product._product_code as 'product_code',
          product._product_name as 'product_name' , brand._brand_name as 'brand_name',
          category._category_name as 'category_name' , sole._sole_name as 'sole_name',
          material._material_name as 'material_name' , COALESCE(SUM(product_details._quantity),0) as 'quantity'
  from product left join product_details on product_details._id_product = product._id_product
  join brand on brand._id_brand = product._id_brand
  join category on category._id_category = product._id_category
  join sole on sole._id_sole = product._id_sole
  join material on material._id_material = product._id_material
  group by product._id_product , product._product_code,
  product._product_name, brand._brand_name,
  category._category_name , sole._sole_name ,
  material._material_name
  order by product._id_product desc
""",nativeQuery = true
  )
  Page<ProductDto> findAllQuantity(Pageable pageable);

  @Query( value = """
          select product._id_product as 'id' , product._product_code as 'product_code',
product._product_name as 'product_name' , brand._brand_name as 'brand_name',
category._category_name as 'category_name' , sole._sole_name as 'sole_name',
material._material_name as 'material_name' , COALESCE(SUM(product_details._quantity),0) as 'quantity'
from product left join product_details on product_details._id_product = product._id_product
join brand on brand._id_brand = product._id_brand
join category on category._id_category = product._id_category
join sole on sole._id_sole = product._id_sole
join material on material._id_material = product._id_material
where(product._product_name = ?1 or ?1 is null or ?1 like '')
and (product._id_brand = ?2 or ?2 is null)
and (product._id_category = ?3 or ?3 is null)
and (product._id_sole = ?4 or ?4 is null)
and (product._id_material = ?5 or ?5 is null)
group by product._id_product , product._product_code,
product._product_name, brand._brand_name,
category._category_name , sole._sole_name ,
material._material_name
order by product._id_product desc
""",nativeQuery = true)
  Page<ProductDto> search(String product_name,Integer brand_id,Integer category_id,Integer sole_id,Integer material_id, Pageable pageable);
}