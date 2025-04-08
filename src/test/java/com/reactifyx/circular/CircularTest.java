package com.reactifyx.circular;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.reactifyx.ComponentScan;
import com.reactifyx.core.ReactifyIoC;
import com.reactifyx.exception.IoCException;
import org.junit.jupiter.api.Test;

@ComponentScan("com.reactifyx.circular")
public class CircularTest {

    @Test
    void testCircular() {
        assertThrows(IoCException.class, () -> {
            ReactifyIoC reactifyIoC = ReactifyIoC.initBeans(CircularTest.class);
        });
    }
}
