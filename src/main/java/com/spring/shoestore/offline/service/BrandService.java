package com.spring.shoestore.offline.service;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Page<Brand> findPaginated(int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
        return brandRepository.findAll(pageable);
    }
}
