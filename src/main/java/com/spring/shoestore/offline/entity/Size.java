package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_size", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 50)
    @NotNull
    @Column(name = "_size_code", nullable = false, length = 50)
    private String sizeCode;

    @jakarta.validation.constraints.Size(max = 50)
    @Nationalized
    @Column(name = "_size_name", length = 50)
    private String sizeName;

}