package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacoclouddomain.entities.TacoOrder;
import com.example.tacocloudmessagingjms.services.receivers.JmsOrderReceiver;
import com.example.tacocloudmessagingjms.services.JmsOrderMessagingService;
import com.example.tacocloudmessagingrabbitmq.services.RabbitOrderMessagingService;
import com.example.tacocloudmessagingrabbitmq.services.receivers.RabbitOrderReceiver;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final JmsOrderMessagingService jmsOrderMessagingService;
    private final JmsOrderReceiver jmsOrderReceiver;
    private final RabbitOrderMessagingService rabbitOrderMessagingService;
    private final RabbitOrderReceiver rabbitOrderReceiver;

    public OrderApiController(OrderRepository orderRepository, JmsOrderMessagingService jmsOrderMessagingService, JmsOrderReceiver jmsOrderReceiver,
                              RabbitOrderMessagingService rabbitOrderMessagingService, RabbitOrderReceiver rabbitOrderReceiver) {
        this.orderRepository = orderRepository;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
        this.jmsOrderReceiver = jmsOrderReceiver;
        this.rabbitOrderMessagingService = rabbitOrderMessagingService;
        this.rabbitOrderReceiver = rabbitOrderReceiver;
    }

    @PostMapping(path = "/jms", consumes = "application/json")
    public TacoOrder postOrderJms(@RequestBody TacoOrder order) {
        jmsOrderMessagingService.sendOrder(order);
        return orderRepository.save(order);
    }

    @GetMapping("/jms")
    public TacoOrder getMessageJms() {
        return jmsOrderReceiver.receiveOrder();
    }

    @PostMapping(path = "/rabbitmq", consumes = "application/json")
    public TacoOrder postOrderRabbitMq(@RequestBody TacoOrder order) {
        rabbitOrderMessagingService.sendOrder(order);
        return orderRepository.save(order);
    }

    @GetMapping("/rabbitmq")
    public TacoOrder getMessageRabbitMq() {
        return rabbitOrderReceiver.receiveOrder();
    }

}
