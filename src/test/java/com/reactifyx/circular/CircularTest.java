package com.reactifyx.circular;

import com.reactifyx.core.ReactifyIoC;
import com.reactifyx.ComponentScan;
import com.reactifyx.exception.IoCException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ComponentScan("com.reactifyx.circular")
public class CircularTest {

    @Test
    void testCircular() {
        assertThrows(IoCException.class, () -> {
            ReactifyIoC reactifyIoC = ReactifyIoC.initBeans(CircularTest.class);
        });
    }
}
