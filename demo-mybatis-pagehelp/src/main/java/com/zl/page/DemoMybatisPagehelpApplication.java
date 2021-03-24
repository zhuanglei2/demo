package com.zl.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.zl.page.mapper"})
public class DemoMybatisPagehelpApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisPagehelpApplication.class, args);
    }

}
