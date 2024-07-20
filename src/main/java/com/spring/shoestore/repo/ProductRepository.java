package com.spring.shoestore.repo;

import com.spring.shoestore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByProductCode(String productCode);
}