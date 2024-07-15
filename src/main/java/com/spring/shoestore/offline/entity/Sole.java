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
@Table(name = "sole")
public class Sole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_sole", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotBlank(message = "Khong bo trong soleCode")
    @Column(name = "_sole_code", nullable = false, length = 50)
    private String soleCode;

    @Size(max = 50)
    @Nationalized
    @NotBlank(message = "Khong bo trong soleName")
    @Column(name = "_sole_name", length = 50)
    private String soleName;

}