package com.ecommerce.ecommercebackend.dataModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) @Getter @Setter
    private Long id;

    @Column(name = "name", nullable = false, unique = true) @Getter @Setter
    private String name;

    @Column(name = "short_description", nullable = false) @Getter @Setter
    private String shortDescription;

    @Column(name = "long_description") @Getter @Setter
    private String longDescription;

    @Column(name = "price", nullable = false) @Getter @Setter
    private Double price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true) @Getter @Setter
    private Inventory inventory;

}
