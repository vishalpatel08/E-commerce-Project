package com.ecommerce.ecommercebackend.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class LoginBody {
    @Getter
    @NotNull
    @NotBlank
    @Setter
    private String username;
    @Getter @NotNull @NotBlank @Setter
    private String password;
}
