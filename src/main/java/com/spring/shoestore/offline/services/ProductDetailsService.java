package com.spring.shoestore.offline.services;

import com.spring.shoestore.offline.dto.ProductDetailDto;
import com.spring.shoestore.offline.entity.ProductDetail;
import com.spring.shoestore.offline.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailRepository productDetailsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SaleRepository saleRepository;

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

            productDetailsRepository.save(productDetail);
        }
    }
}
