package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;
@ToString
@Getter
@Setter
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_brand", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotBlank(message = "Khong bo trong brandCode")
    @Column(name = "_brand_code", nullable = false, length = 20)
    private String brandCode;

    @Size(max = 50)
    @Nationalized
    @NotBlank(message = "Khong bo trong brandName")
    @Column(name = "_brand_name", length = 50)
    private String brandName;

    @Size(max = 255)
    @Nationalized
    @NotBlank(message = "Khong bo trong Description")
    @Column(name = "_description")
    private String description;

}