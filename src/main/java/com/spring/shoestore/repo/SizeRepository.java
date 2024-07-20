package com.spring.shoestore.repo;

import com.spring.shoestore.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}