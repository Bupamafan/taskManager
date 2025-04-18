package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // 查询所有任务
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // 根据ID查询任务
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // 根据状态查询任务
    public List<Task> findTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    // 保存任务（新增或更新）
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // 更新任务状态
    public Optional<Task> updateTaskStatus(Long id, Task.TaskStatus status) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(status);
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    // 删除任务
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // 查询即将到期的任务
    public List<Task> findUpcomingTasks() {
        return taskRepository.findByDueDateBefore(LocalDate.now().plusDays(7));
    }
}