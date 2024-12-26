package com.example.tacocloud.controllers;

import com.example.tacocloud.repositories.OrderRepository;
import com.example.tacoclouddomain.email.EmailOrder;
import com.example.tacoclouddomain.entities.TacoOrder;
import com.example.tacocloudkafka.services.KafkaOrderMessagingService;
import com.example.tacocloudmessagingjms.services.JmsOrderMessagingService;
import com.example.tacocloudmessagingjms.services.receivers.JmsOrderReceiver;
import com.example.tacocloudmessagingrabbitmq.services.RabbitOrderMessagingService;
import com.example.tacocloudmessagingrabbitmq.services.receivers.RabbitOrderReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final JmsOrderMessagingService jmsOrderMessagingService;
    private final JmsOrderReceiver jmsOrderReceiver;
    private final RabbitOrderMessagingService rabbitOrderMessagingService;
    private final RabbitOrderReceiver rabbitOrderReceiver;
    private final KafkaOrderMessagingService kafkaOrderMessagingService;

    public OrderApiController(OrderRepository orderRepository, JmsOrderMessagingService jmsOrderMessagingService, JmsOrderReceiver jmsOrderReceiver,
                              RabbitOrderMessagingService rabbitOrderMessagingService, RabbitOrderReceiver rabbitOrderReceiver,
                              KafkaOrderMessagingService kafkaOrderMessagingService) {
        this.orderRepository = orderRepository;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
        this.jmsOrderReceiver = jmsOrderReceiver;
        this.rabbitOrderMessagingService = rabbitOrderMessagingService;
        this.rabbitOrderReceiver = rabbitOrderReceiver;
        this.kafkaOrderMessagingService = kafkaOrderMessagingService;
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

    @PostMapping(path = "/kafka", consumes = "application/json")
    public TacoOrder postOrderKafka(@RequestBody TacoOrder order) {
        kafkaOrderMessagingService.sendOrder(order);
        return orderRepository.save(order);
    }

    @PostMapping(path = "/fromEmail", consumes = "application/json")
    public EmailOrder fromEmail(@RequestBody EmailOrder order) {
        //TODO Save incoming order in database
        log.info("FROM EMAIL --- ", order.toString());
        return order;
    }

}
