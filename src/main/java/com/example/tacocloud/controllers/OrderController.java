package com.example.tacocloud.controllers;

import com.example.tacocloud.entities.Ingredient;
import com.example.tacocloud.entities.IngredientRef;
import com.example.tacocloud.entities.Taco;
import com.example.tacocloud.entities.TacoOrder;
import com.example.tacocloud.repositories.IngredientRefRepository;
import com.example.tacocloud.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

    private OrderRepository orderRepository;
    private IngredientRefRepository ingredientRefRepository;

    public OrderController(OrderRepository orderRepository, IngredientRefRepository ingredientRefRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRefRepository = ingredientRefRepository;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute TacoOrder tacoOrder, Model model) {
        model.addAttribute("tacoOrder", tacoOrder);
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", tacoOrder);
        tacoOrder = orderRepository.save(tacoOrder);

        for (Taco taco : tacoOrder.getTacos()) {

            for (Ingredient ingredient : taco.getIngredients()) {
                IngredientRef ingredientRef = new IngredientRef();
                ingredientRef.setIngredient(ingredient.getId());
                ingredientRef.setTaco(taco.getId());
                ingredientRefRepository.save(ingredientRef);
            }
        }

        sessionStatus.setComplete();
        return "redirect:/";
    }

}
