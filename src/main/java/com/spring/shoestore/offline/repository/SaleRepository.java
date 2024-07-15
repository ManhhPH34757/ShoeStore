package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}