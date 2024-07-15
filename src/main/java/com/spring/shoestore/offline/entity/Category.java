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
@AllArgsConstructor
@NoArgsConstructor
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
    @NotBlank(message = "Khong bo trong categoryCode")
    @Column(name = "_category_code", nullable = false, length = 20)
    private String categoryCode;

    @Size(max = 50)
    @Nationalized
    @NotBlank(message = "Khong bo trong categoryName")
    @Column(name = "_category_name", length = 50)
    private String categoryName;

    @Size(max = 255)
    @Nationalized
    @NotBlank(message = "Khong bo trong description")
    @Column(name = "_description")
    private String description;

}