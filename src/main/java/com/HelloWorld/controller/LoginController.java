package com.HelloWorld.controller;

import com.HelloWorld.model.User;
import com.HelloWorld.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userId") Integer userId,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.authenticate(userId, password);
        if (user != null) {
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getLastName() + " " + user.getFirstName());
            return "redirect:/products/ec";
        } else {
            model.addAttribute("error", "ユーザーIDまたはパスワードが正しくありません。");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
