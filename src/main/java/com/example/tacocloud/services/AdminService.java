package com.example.tacocloud.services;

import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacoclouddomain.entities.TacoOrder;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final OrderRepository orderRepository;

    public AdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('admin')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @PostAuthorize("hasRole('admin') || " +
            "returnObject.user.username == authentication.name")
    public TacoOrder getOrder(long id) {
        return orderRepository.findById(id).get();
    }

}
