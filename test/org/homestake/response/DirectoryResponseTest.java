package org.homestake.response;

import org.homestake.response.DirectoryResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.Assert.*;

public class DirectoryResponseTest {
    String requestRoute = "/rylan/";
    DirectoryResponse directoryResponse = new DirectoryResponse("public", requestRoute);

    @Test
    public void responseReturnsByteArrayInputStream() throws IOException {
        assertEquals("java.io.SequenceInputStream", directoryResponse.response().getClass().getName());
    }

    @Test
    public void returnsListOfFilesInDirectory() {
        ArrayList files = new ArrayList<String>();
            files.add(".DS_Store");
            files.add("file1");
            files.add("index.html");

        assertEquals(files, directoryResponse.getDirectoryContents());
    }

    @Test
    public void returnsFormattedList() {
        ArrayList files = new ArrayList<String>();
            files.add("index.html");

        assertEquals("<ul><li><a href=\"/rylan/index.html\">index.html</a></li>\n</ul>", directoryResponse.formatList(files));

        ArrayList files2 = new ArrayList<String>();
            files2.add("index.html");
            files2.add("test_directory/");

        assertEquals("<ul><li><a href=\"/rylan/index.html\">index.html</a></li>\n<li><a href=\"/rylan/test_directory/\">test_directory/</a></li>\n</ul>", directoryResponse.formatList(files2));

        requestRoute = "/";
        directoryResponse = new DirectoryResponse("public", requestRoute);

        ArrayList files3 = new ArrayList<String>();
            files3.add("index.html");
            files3.add("test_directory/");

        assertEquals("<ul><li><a href=\"/index.html\">index.html</a></li>\n<li><a href=\"/test_directory/\">test_directory/</a></li>\n</ul>", directoryResponse.formatList(files2));

    }
}
