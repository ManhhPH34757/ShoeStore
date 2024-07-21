package com.spring.shoestore.offline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailDto {
    private Integer idProduct;
    private Integer quantity;
    private BigDecimal price;
    private Integer idColor;
    private Integer idSize;
}
