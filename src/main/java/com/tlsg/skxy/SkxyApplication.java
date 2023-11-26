package com.tlsg.skxy;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.tlsg.skxy.mapper")
public class SkxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkxyApplication.class, args);
    }

}
