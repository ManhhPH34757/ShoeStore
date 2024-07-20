package com.spring.shoestore.repo;

import com.spring.shoestore.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}