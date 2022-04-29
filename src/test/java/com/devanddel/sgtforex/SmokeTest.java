package com.devanddel.sgtforex;

import com.devanddel.sgtforex.controller.ForexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    ForexController forexController;

    @Test
    void contextLoads() {
        assertThat(forexController).isNotNull();
    }

    @Test
    void prueba() {
        System.out.println("test");
    }
}
