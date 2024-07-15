package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}