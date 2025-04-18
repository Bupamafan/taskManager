package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks") // 修改路径，不再使用/api/tasks
public class TaskViewController {

    @GetMapping("")
    public String tasksPage() {
        return "tasks";  // 返回tasks.html模板
    }
}