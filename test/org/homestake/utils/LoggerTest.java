package org.homestake.utils;

import org.junit.*;
import java.io.*;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class LoggerTest {
    Logger logger;

    @Before
    public void setup() {
        logger = new Logger("test.log");
    }

    @After
    public void teardown() throws IOException {
        logger.clear();
    }

    @Test
    public void testPrintMessage() throws IOException {
        String testMessage = "TEST";
        logger.writeMessage(testMessage + "THIS SHOULD GET CLEARED");
        logger.clear();
        logger.writeMessage(testMessage);

        assertEquals(testMessage, new Scanner(new FileReader(logger.logFileName)).useDelimiter("\n").next());
    }
}
