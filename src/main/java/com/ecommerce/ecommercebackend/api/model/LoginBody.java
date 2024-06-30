package com.ecommerce.ecommercebackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

public class LoginBody {
    @Getter @NotNull @NotBlank
    private String username;
    @Getter @NotNull @NotBlank
    private String password;
}
