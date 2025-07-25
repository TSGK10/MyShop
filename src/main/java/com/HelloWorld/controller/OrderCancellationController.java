package com.HelloWorld.controller;

import com.HelloWorld.model.OrderCancellation;
import com.HelloWorld.service.OrderCancellationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderCancellationController {

    @Autowired
    private OrderCancellationService cancellationService;

    @PostMapping("/orders/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId,
                              @RequestParam("reason") String reason,
                              HttpSession session,
                              Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        cancellationService.cancelOrder(orderId, userId, reason);
        model.addAttribute("message", "注文をキャンセルしました。");
        return "redirect:/purchase-history";
    }

    @GetMapping("/cancellation-history")
    public String showCancellationHistory(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<OrderCancellation> cancellations = cancellationService.getCancellationsByUserId(userId);
        model.addAttribute("cancellations", cancellations);
        return "cancellation-history";
    }
}