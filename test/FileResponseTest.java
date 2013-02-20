import org.junit.Test;
import static junit.framework.Assert.*;

public class FileResponseTest {

    @Test
    public void testConstructor() {
        String rootDirectory = "public";
        String requestRoute = "/rylan/index.html";
        FileResponse fileResponse = new FileResponse(rootDirectory, requestRoute);

        assertEquals(rootDirectory, fileResponse.rootDirectory);
        assertEquals(requestRoute, fileResponse.requestRoute);
        assertEquals("java.io.FileInputStream", fileResponse.response().getClass().getName());
    }

    @Test
    public void testSomethin() {
        String rootDirectory = "public";
        String requestRoute = "/asdfa;sdl0asdfa-d=f-23kjfansasdf";
        FileResponse fileResponse = new FileResponse(rootDirectory, requestRoute);

        assertEquals(null, fileResponse.response());
    }

}
