package com.ecommerce.ecommercebackend.api.model;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {

    @Getter @Setter
    private String jwt;

    @Getter @Setter
    private boolean success;

    @Getter @Setter
    private String failureReason;

}
