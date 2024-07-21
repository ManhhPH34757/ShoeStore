package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

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
    @NotNull
    @Column(name = "_brand_code", nullable = false, length = 20)
    @NotBlank(message = "Code cannot be left blank ")
    private String brandCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_brand_name", length = 50)
    @NotBlank(message = "Name cannot be left blank ")
    private String brandName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "_description")
    @NotBlank(message = "Description cannot be left blank ")
    private String description;

}