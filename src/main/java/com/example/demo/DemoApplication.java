package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    static {
        // 在类加载时就设置编码，确保所有系统属性在应用启动前已设置
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.stdout.encoding", "UTF-8");
        System.setProperty("sun.stderr.encoding", "UTF-8");
    }

    public static void main(String[] args) {
        // 打印当前系统编码信息
        System.out.println("默认字符集: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("file.encoding: " + System.getProperty("file.encoding"));
        System.out.println("sun.stdout.encoding: " + System.getProperty("sun.stdout.encoding"));
        System.out.println("sun.stderr.encoding: " + System.getProperty("sun.stderr.encoding"));
        System.out.println("控制台编码测试: 你好，世界！");
        
        SpringApplication.run(DemoApplication.class, args);
    }
}
