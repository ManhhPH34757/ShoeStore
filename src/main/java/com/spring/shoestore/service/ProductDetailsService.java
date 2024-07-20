package com.spring.shoestore.service;


import com.spring.shoestore.dto.ProductDetailDto;
import com.spring.shoestore.entity.ProductDetail;
import com.spring.shoestore.repo.ColorRepository;
import com.spring.shoestore.repo.ProductDetailRepository;
import com.spring.shoestore.repo.ProductRepository;
import com.spring.shoestore.repo.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductDetailsService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    public void saveProductDetails(Integer idProduct, List<ProductDetailDto> productDetails) {
        for (ProductDetailDto detailDto : productDetails) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setIdProduct(productRepository.findById(idProduct).get());
            productDetail.setIdColor(colorRepository.findById(detailDto.getIdColor()).get());
            productDetail.setIdSize(sizeRepository.findById(detailDto.getIdSize()).get());
            productDetail.setQuantity(detailDto.getQuantity());
            productDetail.setPriceNew(detailDto.getPrice());
            productDetail.setPriceOld(detailDto.getPrice());
            productDetail.setStatus("Available");
            productDetail.setDateCreated(Instant.now());
            productDetail.setDateUpdated(Instant.now());

            productDetailRepository.save(productDetail);
        }
    }
}
