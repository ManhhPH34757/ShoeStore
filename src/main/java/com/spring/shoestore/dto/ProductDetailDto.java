package com.spring.shoestore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailDto {
    private Integer idProduct;
    private Integer quantity;
    private BigDecimal price;
    private Integer idColor;
    private Integer idSize;

}
