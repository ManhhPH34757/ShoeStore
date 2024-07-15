package com.spring.shoestore.offline.entity;

import jakarta.persistence.*;
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
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_color", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "_color_code", nullable = false, length = 50)
    private String colorCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_color_name", length = 50)
    private String colorName;

}