package com.reactifyx.configbean;

import com.reactifyx.core.ReactifyIoC;
import com.reactifyx.ComponentScan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.reactifyx.configbean")
public class ConfigBeanTest {
    static ReactifyIoC reactifyIoC;

    @BeforeAll
    static void init() {
        reactifyIoC = ReactifyIoC.initBeans(ConfigBeanTest.class);
    }

    @Test
    void testSingleClassConstruct() {
        ClientClass clientClass = reactifyIoC.getBean(ClientClass.class);
        String run = clientClass.run();
        assertEquals("0 | DB for app version 0", run);
        String scan = clientClass.runScan();
        assertEquals("Scanning for DB for app version 0", scan);
    }
}
