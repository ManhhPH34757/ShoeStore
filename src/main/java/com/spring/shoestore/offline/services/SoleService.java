package com.spring.shoestore.offline.services;

import com.spring.shoestore.offline.entity.Category;
import com.spring.shoestore.offline.entity.Sole;
import com.spring.shoestore.offline.repository.SoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SoleService {
    @Autowired
    SoleRepository soleRepository;
    public Page<Sole> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return soleRepository.findAll(pageable);
    }
}
