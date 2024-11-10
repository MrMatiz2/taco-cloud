package com.example.tacocloud.controllers;

import com.example.tacocloud.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/deleteOrder")
    public String deleteOrder() {
        adminService.deleteAllOrders();
        return "redirect:/admin";
    }

}
