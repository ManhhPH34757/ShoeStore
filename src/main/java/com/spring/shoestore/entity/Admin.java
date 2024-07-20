package com.spring.shoestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_admin", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "_admin_code", nullable = false, length = 20)
    private String adminCode;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Nationalized
    @Column(name = "_last_name", length = 50)
    private String lastName;

    @Size(max = 10)
    @Column(name = "_gender", length = 10)
    private String gender;

    @Size(max = 255)
    @Nationalized
    @Column(name = "_address")
    private String address;

    @Size(max = 15)
    @Column(name = "_phone_number", length = 15)
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "_email")
    private String email;

    @Column(name = "_birthday")
    private LocalDate birthday;

}