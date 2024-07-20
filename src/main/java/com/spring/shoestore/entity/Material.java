package com.spring.shoestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_material", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "_material_code", nullable = false, length = 20)
    private String materialCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_material_name", length = 50)
    private String materialName;

}