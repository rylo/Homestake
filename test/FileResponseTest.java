import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;

public class FileResponseTest {
    String rootDirectory;
    String requestRoute;

    @Test
    public void testConstructor() {
        rootDirectory = "public";
        requestRoute = "/rylan/index.html";
        FileResponse fileResponse = new FileResponse(rootDirectory, requestRoute);

        assertEquals(rootDirectory, fileResponse.rootDirectory);
        assertEquals(requestRoute, fileResponse.requestRoute);
        assertEquals("java.io.SequenceInputStream", fileResponse.response().getClass().getName());
    }

    @Test
    public void testNotFoundResponse() throws IOException {
        rootDirectory = "public";
        requestRoute = "/asdfa;sdl0asdfa-d=f-23kjfansasdf";
        FileResponse fileResponse = new FileResponse(rootDirectory, requestRoute);
        SpecHelper specHelper =  new SpecHelper();
        String response = specHelper.responseString(fileResponse.response());

        assertTrue(response.contains("404") && response.contains("Not Found"));
    }

}
