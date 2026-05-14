package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理脚手架启动类
 */
@SpringBootApplication
@MapperScan("com.example.admin.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("========================================");
        System.out.println("   Admin Scaffold Started Successfully!");
        System.out.println("========================================");
    }
}
