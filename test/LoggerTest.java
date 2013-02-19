import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

public class LoggerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testLoggerConstructor() {
        Logger logger = new Logger();
        assertEquals("Logger", logger.getClass().getName());
    }

    @Test
    public void testPrintMessage() {
        Logger logger = new Logger();
        logger.printMessage("TEST");
        assertEquals("TEST\n", outContent.toString());
    }
}
