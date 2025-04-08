package com.reactifyx.multiple;

import com.reactifyx.core.ReactifyIoC;
import com.reactifyx.ComponentScan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.reactifyx.multiple")
class MultipleTest {
    static ReactifyIoC reactifyIoC;

    @BeforeAll static void init() {
        reactifyIoC = ReactifyIoC.initBeans(MultipleTest.class);
    }

    @Test void testMultipleClassConstruct() {
        Browser browser = reactifyIoC.getBean(Browser.class);
        String text = browser.run();
        assertEquals("This browser run on V8 and CircularDependencyOfV8Engine", text);
    }
}
