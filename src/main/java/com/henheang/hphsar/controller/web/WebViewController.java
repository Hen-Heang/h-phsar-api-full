package com.henheang.hphsar.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebViewController {

    @GetMapping({"/", "/login"})
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("/distributor/dashboard")
    public String distributorDashboard() {
        return "distributor/dashboard";
    }

    @GetMapping("/distributor/products")
    public String distributorProducts() {
        return "distributor/products";
    }

    @GetMapping("/distributor/orders")
    public String distributorOrders() {
        return "distributor/orders";
    }

    @GetMapping("/distributor/categories")
    public String distributorCategories() {
        return "distributor/categories";
    }

    @GetMapping("/distributor/store")
    public String distributorStore() {
        return "distributor/store";
    }

    @GetMapping("/distributor/profile")
    public String distributorProfile() {
        return "distributor/profile";
    }

    // ─── Retailer ───────────────────────────────────────────────────────────────

    @GetMapping("/retailer/dashboard")
    public String retailerDashboard() { return "retailer/dashboard"; }

    @GetMapping("/retailer/stores")
    public String retailerStores() { return "retailer/stores"; }

    @GetMapping("/retailer/cart")
    public String retailerCart() { return "retailer/cart"; }

    @GetMapping("/retailer/orders")
    public String retailerOrders() { return "retailer/orders"; }

    @GetMapping("/retailer/history")
    public String retailerHistory() { return "retailer/history"; }

    @GetMapping("/retailer/profile")
    public String retailerProfile() { return "retailer/profile"; }
}