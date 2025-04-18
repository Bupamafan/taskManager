package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password,
                          Model model) {
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        
        User user = new User(username, password);
        userService.saveUser(user);
        return "redirect:/login";
    }
}