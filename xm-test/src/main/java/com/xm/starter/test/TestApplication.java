package com.xm.starter.test;

import cn.hutool.extra.spring.SpringUtil;
import com.xm.starter.base.api.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        UserService userService = SpringUtil.getBean(UserService.class);
        System.out.println(userService);
    }
}
