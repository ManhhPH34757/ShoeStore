package com.spring.shoestore.offline.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetailDto {
    private Integer idProduct;
    private Integer quantity;
    private BigDecimal price;
    private Integer idColor;
    private Integer idSize;
}

