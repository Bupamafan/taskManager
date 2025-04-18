package com.example.demo.controller; // 定义包名，用于组织类的结构

import com.example.demo.entity.Task; // 导入任务实体类
import com.example.demo.service.TaskService; // 导入任务服务类，用于处理业务逻辑
import org.springframework.beans.factory.annotation.Autowired; // 导入自动装配注解
import org.springframework.http.HttpStatus; // 导入HTTP状态码
import org.springframework.http.ResponseEntity; // 导入响应实体，用于封装HTTP响应
import org.springframework.web.bind.annotation.*; // 导入Web相关注解

import java.util.List; // 导入List集合
import java.util.Optional; // 导入Optional类，用于处理可能为空的值

@RestController // 标记这个类是REST控制器，会自动将返回值转换为JSON格式
@RequestMapping("/api/tasks") // 指定这个控制器处理的基础URL路径
public class TaskController {

    private final TaskService taskService; // 声明任务服务对象，用于处理业务逻辑

    @Autowired // 自动注入TaskService实例
    public TaskController(TaskService taskService) { // 构造函数，Spring会自动注入TaskService
        this.taskService = taskService; // 初始化taskService字段
    }

    // 获取所有任务
    @GetMapping // 处理GET请求，路径为/api/tasks
    public ResponseEntity<List<Task>> getAllTasks() { // 返回ResponseEntity对象，包含任务列表和状态码
        List<Task> tasks = taskService.findAllTasks(); // 调用服务层方法获取所有任务
        return new ResponseEntity<>(tasks, HttpStatus.OK); // 返回任务列表和200 OK状态码
    }

    // 根据ID获取任务
    @GetMapping("/{id}") // 处理GET请求，路径为/api/tasks/{id}，其中{id}是路径变量
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) { // @PathVariable表示从URL路径中获取id值
        Optional<Task> task = taskService.findTaskById(id); // 调用服务层方法根据ID查找任务
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // 如果找到任务，返回任务和200 OK状态码
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 如果未找到任务，返回404 NOT FOUND状态码
    }

    // 根据状态过滤任务
    @GetMapping("/status/{status}") // 处理GET请求，路径为/api/tasks/status/{status}
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) { // 从URL路径中获取status值
        try {
            Task.TaskStatus taskStatus = Task.TaskStatus.valueOf(status.toUpperCase()); // 将字符串转换为枚举值
            List<Task> tasks = taskService.findTasksByStatus(taskStatus); // 调用服务层方法根据状态查找任务
            return new ResponseEntity<>(tasks, HttpStatus.OK); // 返回任务列表和200 OK状态码
        } catch (IllegalArgumentException e) { // 如果状态值无效（不是枚举中定义的值）
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 返回400 BAD REQUEST状态码
        }
    }

    // 创建新任务
    @PostMapping // 处理POST请求，路径为/api/tasks
    public ResponseEntity<Task> createTask(@RequestBody Task task) { // @RequestBody表示从请求体中获取任务对象
        Task savedTask = taskService.saveTask(task); // 调用服务层方法保存任务
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED); // 返回保存后的任务和201 CREATED状态码
    }

    // 更新任务
    @PutMapping("/{id}") // 处理PUT请求，路径为/api/tasks/{id}
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) { // 从URL路径中获取id值，从请求体中获取任务对象
        Optional<Task> existingTask = taskService.findTaskById(id); // 调用服务层方法根据ID查找任务
        if (existingTask.isPresent()) { // 如果找到任务
            task.setId(id); // 设置任务ID
            Task updatedTask = taskService.saveTask(task); // 调用服务层方法保存更新后的任务
            return new ResponseEntity<>(updatedTask, HttpStatus.OK); // 返回更新后的任务和200 OK状态码
        } else { // 如果未找到任务
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 返回404 NOT FOUND状态码
        }
    }

    // 更新任务状态
    @PatchMapping("/{id}/status") // 处理PATCH请求，路径为/api/tasks/{id}/status
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest statusUpdate) { // 从URL路径中获取id值，从请求体中获取状态更新请求
        try {
            Task.TaskStatus status = Task.TaskStatus.valueOf(statusUpdate.getStatus().toUpperCase()); // 将字符串转换为枚举值
            Optional<Task> updatedTask = taskService.updateTaskStatus(id, status); // 调用服务层方法更新任务状态
            return updatedTask.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // 如果更新成功，返回更新后的任务和200 OK状态码
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 如果未找到任务，返回404 NOT FOUND状态码
        } catch (IllegalArgumentException e) { // 如果状态值无效
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 返回400 BAD REQUEST状态码
        }
    }

    // 删除任务
    @DeleteMapping("/{id}") // 处理DELETE请求，路径为/api/tasks/{id}
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) { // 从URL路径中获取id值
        Optional<Task> existingTask = taskService.findTaskById(id); // 调用服务层方法根据ID查找任务
        if (existingTask.isPresent()) { // 如果找到任务
            taskService.deleteTask(id); // 调用服务层方法删除任务
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 返回204 NO CONTENT状态码，表示删除成功
        } else { // 如果未找到任务
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 返回404 NOT FOUND状态码
        }
    }

    // 状态更新请求的内部类，用于接收状态更新请求
    public static class StatusUpdateRequest {
        private String status; // 状态字符串

        public String getStatus() { // 获取状态
            return status;
        }

        public void setStatus(String status) { // 设置状态
            this.status = status;
        }
    }
}