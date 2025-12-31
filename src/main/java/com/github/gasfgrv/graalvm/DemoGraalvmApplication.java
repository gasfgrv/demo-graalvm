package com.github.gasfgrv.graalvm;

import com.github.gasfgrv.graalvm.infrastructure.config.NativeImageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(NativeImageConfig.class)
public class DemoGraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGraalvmApplication.class, args);
    }

}
