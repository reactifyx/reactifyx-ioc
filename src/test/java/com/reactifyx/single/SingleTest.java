package com.reactifyx.single;

import com.reactifyx.core.ReactifyIoC;
import com.reactifyx.ComponentScan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
