package com.spring.shoestore.offline.services;

import com.spring.shoestore.offline.dto.ProductDto;
import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandService {
    @Autowired
    BrandRepository brandRepository;
    public Page<Brand> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return brandRepository.findAll(pageable);
    }
}
