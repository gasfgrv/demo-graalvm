package com.github.gasfgrv.demo_graalvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.github.gasfgrv.demo_graalvm")
@SpringBootApplication(scanBasePackages = "com.github.gasfgrv.demo_graalvm")
@ComponentScan(basePackages = "com.github.gasfgrv.demo_graalvm")
public class DemoGraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGraalvmApplication.class, args);
    }

}
