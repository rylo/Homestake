package org.homestake.utils;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;

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

    @Test
    public void testGenerateMimeType() throws IOException {
        assertEquals("text/html", fileChecker.generateFileMimeType("public/rylan/index.html"));
        assertEquals("video/mp4", fileChecker.generateFileMimeType("public/rylan/sample.mp4"));
        assertEquals("application/pdf", fileChecker.generateFileMimeType("public/rylan/sample.pdf"));
    }

    @Test
    public void testSupportedFileTypes() {
        assertEquals("video/mp4", fileChecker.supportedFileTypes().get("mp4"));
        assertEquals("audio/mpeg", fileChecker.supportedFileTypes().get("mp3"));
        assertEquals("text/html", fileChecker.supportedFileTypes().get("html"));
        assertEquals("application/pdf", fileChecker.supportedFileTypes().get("pdf"));
        assertEquals("image/jpeg", fileChecker.supportedFileTypes().get("jpg"));
        assertEquals("image/gif", fileChecker.supportedFileTypes().get("gif"));
    }

    @Test
    public void testGetFileExtension() {
        assertEquals("mp4", fileChecker.getFileExtension("public/rylan/sample.mp4"));
        assertEquals("pdf", fileChecker.getFileExtension("public/rylan/sample.pdf"));
    }

}
