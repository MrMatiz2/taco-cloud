package com.example.tacocloud.controllers;

import com.example.tacocloud.configuration.OrderProps;
import com.example.tacocloud.entities.TacoOrder;
import com.example.tacocloud.entities.Users;
import com.example.tacocloud.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderProps orderProps;

    public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute TacoOrder tacoOrder, Model model, @AuthenticationPrincipal Users user) {
        tacoOrder.setDeliveryName(user.getFullname());
        tacoOrder.setDeliveryStreet(user.getStreet());
        tacoOrder.setDeliveryCity(user.getCity());
        tacoOrder.setDeliveryState(user.getState());
        tacoOrder.setDeliveryZip(user.getZip());
        model.addAttribute("tacoOrder", tacoOrder);
        return "orderForm";
    }

    @GetMapping
    public String orders(Model model, @AuthenticationPrincipal Users user) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orders";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus,
                               @AuthenticationPrincipal Users user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.setUser(user);

        log.info("Order submitted: {}", tacoOrder);
        orderRepository.save(tacoOrder);

        sessionStatus.setComplete();
        return "redirect:/";
    }

}
