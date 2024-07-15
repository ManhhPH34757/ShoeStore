package com.spring.shoestore.offline.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductDto {
    @Id
    private Integer id;
    private String product_code;
    private String product_name;
    private String brand_name;
    private String category_name;
    private String sole_name;
    private String material_name;
    private Integer quantity;

}
