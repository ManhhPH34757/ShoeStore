package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_product", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "_product_code", nullable = false, length = 50)
    @NotBlank(message = "Code cannot be left blank ")
    private String productCode;

    @Size(max = 100)
    @Nationalized
    @Column(name = "_product_name", length = 100)
    @NotBlank(message = "Name cannot be left blank ")
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "_id_brand")
    @NotBlank(message = "Id brand cannot be left blank ")
    private Brand idBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "_id_category")
    @NotBlank(message = "Id category cannot be left blank ")
    private Category idCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "_id_sole")
    @NotBlank(message = "Id sole cannot be left blank ")
    private Sole idSole;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "_id_material")
    @NotBlank(message = "Id material cannot be left blank ")
    private Material idMaterial;

    @Column(name = "_date_created")
    private Instant dateCreated;

    @Column(name = "_date_updated")
    private Instant dateUpdated;

}