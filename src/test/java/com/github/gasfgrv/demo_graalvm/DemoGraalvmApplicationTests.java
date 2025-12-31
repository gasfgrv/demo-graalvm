package com.github.gasfgrv.demo_graalvm;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DemoGraalvmApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThat(4 % 2).isEven();
    }

}
