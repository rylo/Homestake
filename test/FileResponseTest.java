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
        assertEquals("FileResponse", fileResponse.getClass().getName());
    }

    @Test
    public void testSomethin() {

    }

}
