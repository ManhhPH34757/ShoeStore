package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}