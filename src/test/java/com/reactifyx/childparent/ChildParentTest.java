package com.reactifyx.childparent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reactifyx.ComponentScan;
import com.reactifyx.core.ReactifyIoC;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@ComponentScan("com.reactifyx.childparent")
public class ChildParentTest {

    static ReactifyIoC reactifyIoC;

    @BeforeAll
    static void init() {
        reactifyIoC = reactifyIoC.initBeans(ChildParentTest.class);
    }

    @Test
    void testChildParent() {
        Child child = reactifyIoC.getBean(Child.class);
        String text = child.test();
        assertEquals("Dependency", text);
    }
}
