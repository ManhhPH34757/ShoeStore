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
@NoArgsConstructor
@AllArgsConstructor
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
    @NotBlank(message = "Khong bo trong materialCode")
    @Column(name = "_material_code", nullable = false, length = 20)
    private String materialCode;

    @Size(max = 50)
    @Nationalized
    @NotBlank(message = "Khong bo trong materialName")
    @Column(name = "_material_name", length = 50)
    private String materialName;

}