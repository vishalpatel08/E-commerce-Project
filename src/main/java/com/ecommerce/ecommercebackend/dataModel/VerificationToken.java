package com.ecommerce.ecommercebackend.dataModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name="verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) @Getter @Setter
    private Long id;

    @Lob
    @Column(name="token", nullable = false, unique = true) @Getter @Setter
    private String token;

    @Column(name="created_timestamp", nullable = false) @Getter @Setter
    private Timestamp createdTimestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id",nullable = false) @Getter @Setter
    private LocalUser user;
}
