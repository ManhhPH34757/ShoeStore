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
@Table(name = "sole")
public class Sole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_sole", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "_sole_code", nullable = false, length = 50)
    private String soleCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_sole_name", length = 50)
    private String soleName;

}