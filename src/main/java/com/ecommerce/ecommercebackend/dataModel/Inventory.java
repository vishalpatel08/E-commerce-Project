package com.ecommerce.ecommercebackend.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true) @Getter @Setter
    private Long id;

    @JsonIgnore
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name="product_id", nullable = false, unique = true) @Getter @Setter
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
