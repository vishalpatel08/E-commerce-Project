package com.ecommerce.ecommercebackend.service;

import com.ecommerce.ecommercebackend.dataModel.Product;
import com.ecommerce.ecommercebackend.dataModel.dao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao){
        this.productDao = productDao;
    }

    public List<Product> getProducts(){
        return productDao.findAll();
    }
}
