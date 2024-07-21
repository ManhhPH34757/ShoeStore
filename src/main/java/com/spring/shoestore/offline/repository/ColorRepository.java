package com.spring.shoestore.offline.repository;

import com.spring.shoestore.offline.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}