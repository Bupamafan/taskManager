package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 声明这是一个 Web 控制器
public class HelloController {

    @GetMapping("/hello") // 访问路径是 /hello
    public String sayHello() {
        return "Hello, Spring!"; // 返回这段文字
    }

    @GetMapping("/greet")
    public String greetUser(@RequestParam String name) { // 接收 URL 参数 name
        return "Hello, " + name + "! 欢迎使用 Spring！";
    }

    // 必须登录才能访问
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // 必须拥有 ADMIN 角色
    public String adminPage() {
        return "欢迎管理员！这是机密页面~";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')") // 必须拥有 USER 角色
    public String userPage() {
        return "普通用户专属页面";
    }
}
