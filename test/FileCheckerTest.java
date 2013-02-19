import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class FileCheckerTest {
    FileChecker fileChecker = new FileChecker();

    @Test
    public void testIfDirectoryExists() {
        assertTrue(fileChecker.directoryExists("/src/"));
        assertFalse(fileChecker.directoryExists("/aslasdfasdfsdf/"));
        assertFalse(fileChecker.directoryExists("/aslasdfasdfsdf/", "asdfs/"));
        assertFalse(fileChecker.directoryExists("/test/FileCheckerTest.java"));
        assertFalse(fileChecker.directoryExists("/test/", "FileCheckerTest.java"));
        assertTrue(fileChecker.directoryExists("/Library/", "Java/"));
    }

    @Test
    public void testIfFileExists() {
        assertTrue(fileChecker.fileExists("test/FileCheckerTest.java"));
        assertTrue(fileChecker.fileExists("/bin/", "kill"));
        assertFalse(fileChecker.fileExists("/test/", "alskdjfansldkfas.java"));
        assertFalse(fileChecker.fileExists("/test/alskdjfansldkfas.java"));
    }

}
