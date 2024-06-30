package com.ecommerce.ecommercebackend.dataModel.dao;

import com.ecommerce.ecommercebackend.dataModel.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDao extends ListCrudRepository<Product, Long > {
}
