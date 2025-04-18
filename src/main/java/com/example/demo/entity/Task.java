package com.example.demo.entity; // 定义包名，用于组织类的结构

import jakarta.persistence.*; // 导入JPA相关注解，用于对象关系映射
import java.time.LocalDate; // 导入Java 8日期类型，用于表示日期

@Entity // 标记这个类是一个实体类，将会映射到数据库中的表
@Table(name = "task") // 指定映射到数据库中的表名为"task"
public class Task {

    @Id // 标记这个字段是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定主键生成策略为自增长
    private Long id; // 任务ID，作为主键

    @Column(nullable = false) // 指定这个字段映射到数据库中的列，且不允许为null
    private String title; // 任务标题

    @Column(length = 1000) // 指定这个字段映射到数据库中的列，且最大长度为1000
    private String description; // 任务描述

    @Column(name = "due_date") // 指定这个字段映射到数据库中的列名为"due_date"
    private LocalDate dueDate; // 任务截止日期

    @Column(nullable = false) // 指定这个字段映射到数据库中的列，且不允许为null
    @Enumerated(EnumType.STRING) // 指定枚举类型在数据库中以字符串形式存储
    private TaskStatus status; // 任务状态，使用枚举类型

    // 枚举类型定义任务状态，在Java中定义，在数据库中以字符串形式存储
    public enum TaskStatus {
        NOT_STARTED, // 未开始
        IN_PROGRESS, // 进行中
        COMPLETED    // 已完成
    }

    // 无参构造函数，JPA要求实体类必须有一个无参构造函数
    public Task() {
    }

    // 带参数的构造函数，方便创建任务对象
    public Task(String title, String description, LocalDate dueDate, TaskStatus status) {
        this.title = title;        // 设置任务标题
        this.description = description;  // 设置任务描述
        this.dueDate = dueDate;    // 设置任务截止日期
        this.status = status;      // 设置任务状态
    }

    // 以下是Getter和Setter方法，用于获取和设置对象的属性
    
    // 获取任务ID
    public Long getId() {
        return id;
    }

    // 设置任务ID
    public void setId(Long id) {
        this.id = id;
    }

    // 获取任务标题
    public String getTitle() {
        return title;
    }

    // 设置任务标题
    public void setTitle(String title) {
        this.title = title;
    }

    // 获取任务描述
    public String getDescription() {
        return description;
    }

    // 设置任务描述
    public void setDescription(String description) {
        this.description = description;
    }

    // 获取任务截止日期
    public LocalDate getDueDate() {
        return dueDate;
    }

    // 设置任务截止日期
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // 获取任务状态
    public TaskStatus getStatus() {
        return status;
    }

    // 设置任务状态
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // 重写toString方法，方便打印对象信息，在日志记录时很有用
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +                       // 任务ID
                ", title='" + title + '\'' +       // 任务标题
                ", description='" + description + '\'' + // 任务描述
                ", dueDate=" + dueDate +           // 任务截止日期
                ", status=" + status +             // 任务状态
                '}';
    }
}