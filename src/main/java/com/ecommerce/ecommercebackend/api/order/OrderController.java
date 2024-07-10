package com.ecommerce.ecommercebackend.api.order;

import com.ecommerce.ecommercebackend.dataModel.Eorder;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import com.ecommerce.ecommercebackend.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public List<Eorder> getOrders(@AuthenticationPrincipal LocalUser user){
        return orderService.getOrders(user);
    }
}
