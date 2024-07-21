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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_category", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "_category_code", nullable = false, length = 20)
    @NotBlank(message = "Code cannot be left blank ")
    private String categoryCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_category_name", length = 50)
    @NotBlank(message = "Name cannot be left blank ")
    private String categoryName;

    @Size(max = 255)
    @Nationalized
    @Column(name = "_description")
    @NotBlank(message = "Description cannot be left blank ")
    private String description;

}