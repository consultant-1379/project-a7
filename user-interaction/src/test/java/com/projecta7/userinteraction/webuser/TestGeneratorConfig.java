package com.projecta7.userinteraction.webuser;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TestGeneratorConfig {

    @Autowired
    GeneratorConfig generator;


    @DisplayName("Test Generator Config format")
    @Test
    void testFormat() {
        assertTrue(generator.format("Hello").contains("<form action="));
    }
}
