package com.spring.shoestore.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountAdminDTO {
    // Các trường từ AccountAdmin
    private Integer id;
    private String userName;
    private String status;
    private String role;

    // Các trường từ Admin
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String birthday;
    private String gender;

}
