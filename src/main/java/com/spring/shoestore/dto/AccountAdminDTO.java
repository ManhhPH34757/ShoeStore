package com.spring.shoestore.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminDTO {
    // Các trường từ AccountAdmin
    @NotNull(message = "ID cannot be null")
    private Integer id;
    @NotBlank(message = "Username cannot be empty")
    private String userName;
    @NotBlank(message = "Status cannot be empty")
    private String status;
    @NotBlank(message = "Role cannot be empty")
    private String role;

    // Các trường từ Admin
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Address cannot be empty")
    private String address;
    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;
    @NotBlank(message = "Birthday cannot be empty")
    private String birthday;
    @NotBlank(message = "Gender cannot be empty")
    private String gender;


}
