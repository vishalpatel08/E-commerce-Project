package com.ecommerce.ecommercebackend.service;

import com.ecommerce.ecommercebackend.dataModel.Eorder;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import com.ecommerce.ecommercebackend.dataModel.dao.WebOrderDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private WebOrderDao webOrderDao;

    public OrderService(WebOrderDao webOrderDao) {
        this.webOrderDao = webOrderDao;
    }
    public List<Eorder> getOrders(LocalUser user){
        System.out.println(user);
        System.out.println(webOrderDao.findByUser(user));
        System.out.println("2");
        return webOrderDao.findByUser(user);
    }
}
