package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacoclouddomain.entities.TacoOrder;
import com.example.tacocloudmessagingjms.services.receivers.JmsOrderReceiver;
import com.example.tacocloudmessagingjms.services.JmsOrderMessagingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final JmsOrderMessagingService jmsOrderMessagingService;
    private final JmsOrderReceiver jmsOrderReceiver;

    public OrderApiController(OrderRepository orderRepository, JmsOrderMessagingService jmsOrderMessagingService, JmsOrderReceiver jmsOrderReceiver) {
        this.orderRepository = orderRepository;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
        this.jmsOrderReceiver = jmsOrderReceiver;
    }

    @PostMapping(consumes = "application/json")
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        jmsOrderMessagingService.sendOrder(order);
        return orderRepository.save(order);
    }

    @GetMapping("/getMessage")
    public TacoOrder getOrderJms() {
        return jmsOrderReceiver.receiveOrder();
    }

}
