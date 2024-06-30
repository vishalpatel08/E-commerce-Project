package com.ecommerce.ecommercebackend.api.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class RegistrationBody {

    @Getter @Setter @NonNull @NotBlank @Size(min=4, max=10)
    private String username;

    @Getter @Setter @NonNull @NotBlank @Email
    private String email;

    @Getter @Setter @NonNull @NotBlank @Size(min=6, max=12) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;

    @Getter @Setter @NonNull @NotBlank
    private String firstName;

    @Getter @Setter @NonNull @NotBlank
    private String lastName;

}
