package com.spring.shoestore.offline.service;

import com.spring.shoestore.offline.entity.Brand;
import com.spring.shoestore.offline.entity.Material;
import com.spring.shoestore.offline.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public Page<Material> findPaginated(int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,sort);
        return materialRepository.findAll(pageable);
    }
}
