package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}