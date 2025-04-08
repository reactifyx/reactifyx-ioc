package com.reactifyx.single;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reactifyx.ComponentScan;
import com.reactifyx.core.ReactifyIoC;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@ComponentScan("com.reactifyx.single")
public class SingleTest {
    static ReactifyIoC reactifyIoC;

    @BeforeAll
    static void init() {
        reactifyIoC = ReactifyIoC.initBeans(SingleTest.class);
    }

    @Test
    void testSingleClassConstruct() {
        TestComponent testComponent = reactifyIoC.getBean(TestComponent.class);
        String name = testComponent.getName();
        assertEquals("Test", name);
    }
}
