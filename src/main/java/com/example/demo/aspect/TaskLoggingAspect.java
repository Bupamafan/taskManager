package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(TaskLoggingAspect.class);

    // 定义切点：TaskService中的所有方法
    @Pointcut("execution(* com.example.demo.service.TaskService.*(..))")
    public void taskServiceMethods() {}

    // 方法执行前记录日志
    // 修改日志输出方式，使用占位符
    @Before("taskServiceMethods()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("执行方法: {} 参数: {}", methodName, args);
    }

    // 方法成功返回后记录日志
    @AfterReturning(pointcut = "taskServiceMethods()", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("方法 {} 执行完成，返回结果: {}", methodName, result);
    }

    // 特别关注任务状态更新
    @AfterReturning(
            pointcut = "execution(* com.example.demo.service.TaskService.updateTaskStatus(..))",
            returning = "result")
    public void logTaskStatusUpdate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        logger.info("任务状态更新 - 任务ID: {}, 新状态: {}, 更新结果: {}", 
                args[0], args[1], result);
    }
}