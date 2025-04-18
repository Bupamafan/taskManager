package com.example.demo.repository;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 根据状态查询任务
    List<Task> findByStatus(Task.TaskStatus status);
    
    // 根据截止日期查询任务
    List<Task> findByDueDateBefore(LocalDate date);
    
    // 根据标题包含关键字查询任务
    List<Task> findByTitleContaining(String keyword);
}