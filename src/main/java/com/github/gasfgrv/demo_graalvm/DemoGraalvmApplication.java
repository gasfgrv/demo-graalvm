package com.github.gasfgrv.demo_graalvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DemoGraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGraalvmApplication.class, args);
    }

}
