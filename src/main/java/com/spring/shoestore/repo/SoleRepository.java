package com.spring.shoestore.repo;

import com.spring.shoestore.entity.Sole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoleRepository extends JpaRepository<Sole, Integer> {
}