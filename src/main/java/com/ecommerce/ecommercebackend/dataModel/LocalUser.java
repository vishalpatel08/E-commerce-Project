package com.ecommerce.ecommercebackend.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local_user")
public class LocalUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false) @Getter @Setter
    private Long id;

    @Column(name = "email", nullable = false, unique = true) @Getter @Setter
    private String email;

    @Column(name="username", nullable = false, unique = true) @Getter @Setter
    private String username;

    @Column(name="password", nullable = false, length = 30) @Getter @Setter
    private String password;

    @Column(name="first_name", nullable = false) @Getter @Setter
    private String firstName;

    @Column(name="last_name", nullable = false) @Getter @Setter
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "localUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Getter @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();


    @Setter
    @Column(name="email_verified", nullable = false)
    private boolean emailVerified = false;

    public Boolean isEmailVerified(){
        return emailVerified;
    }



}
