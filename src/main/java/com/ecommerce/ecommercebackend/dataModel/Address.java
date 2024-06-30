package com.ecommerce.ecommercebackend.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id", nullable = false) @Getter @Setter
    private Long id;

    @Column (name="address_line_1", nullable = false, length = 256) @Getter @Setter
    private String addressLine1;

    @Column(name = "address_line_2", length = 60) @Getter @Setter
    private String addressLine2;

    @Column(name="city", nullable = false, length = 60) @Getter @Setter
    private String city;

    @Column(name = "country", nullable = false, length = 60) @Getter @Setter
    private String country;

    //@JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser localUser;
}
