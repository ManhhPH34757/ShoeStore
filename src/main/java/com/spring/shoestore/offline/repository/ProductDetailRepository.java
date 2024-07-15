package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Product;
import com.spring.shoestore.offline.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findProductDetailByIdProduct(Product idProduct);
}