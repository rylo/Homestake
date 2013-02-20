import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.*;

public class DirectoryResponseTest {
    String requestRoute = "/rylan";
    DirectoryResponse directoryResponse = new DirectoryResponse("public", requestRoute);

    @Test
    public void responseReturnsByteArrayInputStream() {
        assertEquals("java.io.ByteArrayInputStream", directoryResponse.response().getClass().getName());
    }

    @Test
    public void returnsListOfFilesInDirectory() {
        ArrayList files = new ArrayList<String>();
            files.add("index.html");

        assertEquals(files, directoryResponse.getDirectoryContents());
    }

    @Test
    public void returnsFormattedList() {
        ArrayList files = new ArrayList<String>();
            files.add("index.html");

        assertEquals("<ul><li>index.html</li>\n</ul>", directoryResponse.formatList(files));

        ArrayList files2 = new ArrayList<String>();
            files2.add("index.html");
            files2.add("test_directory/");

        assertEquals("<ul><li>index.html</li>\n<li>test_directory/</li>\n</ul>", directoryResponse.formatList(files2));
    }
}
