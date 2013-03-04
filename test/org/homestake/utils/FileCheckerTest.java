package org.homestake.utils;

import org.homestake.utils.FileChecker;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class FileCheckerTest {
    FileChecker fileChecker = new FileChecker("public");

    @Test
    public void testIfDirectoryExists() {
        assertTrue(fileChecker.directoryExists("/rylan/"));
        assertFalse(fileChecker.directoryExists("/test/"));
        assertFalse(fileChecker.directoryExists("/rylan/index.html"));
    }

    @Test
    public void testIfFileExists() {
        assertTrue(fileChecker.fileExists("/rylan/index.html"));
        assertFalse(fileChecker.fileExists("/test/alskdjfansldkfas.java"));
    }

}
