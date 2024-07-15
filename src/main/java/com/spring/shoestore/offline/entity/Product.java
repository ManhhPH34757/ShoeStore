package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
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
    @NotBlank(message = "** Khong bo trong productCode")
    @Column(name = "_product_code", nullable = false, length = 50)
    private String productCode;

    @Size(max = 100)
    @Nationalized
    @NotBlank(message = "** Khong bo trong productName")
    @Column(name = "_product_name", length = 100)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "** Khong bo trong Brand")
    @JoinColumn(name = "_id_brand")
    private Brand idBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "** Khong bo trong Category")
    @JoinColumn(name = "_id_category")
    private Category idCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "** Khong bo trong Sole")
    @JoinColumn(name = "_id_sole")
    private Sole idSole;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(message = "** Khong bo trong Material")
    @JoinColumn(name = "_id_material")
    private Material idMaterial;

    @Column(name = "_date_created")
    private Instant dateCreated;

    @Column(name = "_date_updated")
    private Instant dateUpdated;

}