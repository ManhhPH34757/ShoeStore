package com.spring.shoestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "account_admin")
public class AccountAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id_account_admin", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "_id_admin", nullable = false)
    private Admin idAdmin;

    @Size(max = 50)
    @Column(name = "_user_name", length = 50)
    private String userName;

    @Column(name = "_password")
    private byte[] password;

    @Size(max = 30)
    @Column(name = "_role", length = 30)
    private String role;

    @Size(max = 255)
    @Nationalized
    @Column(name = "_status")
    private String status;


}