package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_sale", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "_sale_code", nullable = false, length = 50)
    private String saleCode;

    @Nationalized
    @Lob
    @Column(name = "_sale_name")
    private String saleName;

    @Column(name = "_percent")
    private Double percent;

    @Column(name = "_date_created")
    private Instant dateCreated;

    @Column(name = "_deadline")
    private Instant deadline;

}