package com.ecommerce.ecommercebackend.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="eorder")
public class Eorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) @Getter @Setter
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false) @Getter @Setter
    private LocalUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false) @Getter @Setter
    private Address address;

    @OneToMany(mappedBy = "eorder", cascade = CascadeType.REMOVE, orphanRemoval = true) @Getter @Setter
    private List<EorderQuantity> quantities = new ArrayList<>();

}
