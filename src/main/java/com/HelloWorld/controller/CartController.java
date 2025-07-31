package com.HelloWorld.controller;

import org.springframework.ui.Model;
import com.HelloWorld.model.CartProduct;
import com.HelloWorld.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<CartProduct> cartProducts = cartService.getCartProducts(userId);
        model.addAttribute("cartProducts", cartProducts);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam int productId, @RequestParam int quantity, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam int cartProductId) {
        cartService.removeProduct(cartProductId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam int cartProductId, @RequestParam int quantity) {
        cartService.updateQuantity(cartProductId, quantity);
        return "redirect:/cart";
    }
}

