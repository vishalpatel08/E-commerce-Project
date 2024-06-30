package com.ecommerce.ecommercebackend.dataModel.dao;

import com.ecommerce.ecommercebackend.dataModel.Eorder;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDao extends ListCrudRepository<Eorder, Long> {
    List<Eorder> findByUser(LocalUser user);

}
