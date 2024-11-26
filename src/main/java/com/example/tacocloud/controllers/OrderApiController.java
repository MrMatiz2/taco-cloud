package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacoclouddomain.entities.TacoOrder;
import com.example.tacocloudmessagingjms.services.JmsOrderMessagingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final JmsOrderMessagingService jmsOrderMessagingService;

    public OrderApiController(OrderRepository orderRepository, JmsOrderMessagingService jmsOrderMessagingService) {
        this.orderRepository = orderRepository;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
    }

    @PostMapping(consumes = "application/json")
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        jmsOrderMessagingService.sendOrder(order);
        return orderRepository.save(order);
    }

}
