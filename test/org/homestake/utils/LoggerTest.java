package org.homestake.utils;

import org.junit.*;
import java.io.*;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class LoggerTest {
    Long threadID = Long.valueOf(1);

    @Before
    public void setup() {
        Logger.setLogFile("test.log");
        Logger.newQueue(threadID);
        assertEquals("", Logger.getQueue(threadID).toString());
    }

    @After
    public void teardown() throws IOException {
        Logger.clearFile();
        assertEquals(false, new Scanner(new FileReader(Logger.logFileName)).hasNext());
    }

    @Test
    public void testPrintMessage() throws IOException {
        String testMessage = "TEST";
        Logger.addToQueue(threadID, testMessage);
        Logger.writeQueuedMessages(threadID);

        assertEquals(testMessage, new Scanner(new FileReader(Logger.logFileName)).useDelimiter("\n").next());
    }

    @Test
    public void addToMessageQueue() {
        assertEquals("", Logger.getQueue(threadID).toString());
        Logger.addToQueue(threadID,"ohi");
        assertEquals("ohi", Logger.getQueue(threadID).toString());
        Logger.addToQueue(threadID," dere");
        assertEquals("ohi dere", Logger.getQueue(threadID).toString());
        Logger.destroyQueue(threadID);
        assertEquals(null, Logger.getQueue(threadID));
    }
}
