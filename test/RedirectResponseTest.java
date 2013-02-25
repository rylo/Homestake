import org.junit.Test;
import static junit.framework.Assert.*;

import java.io.IOException;

public class RedirectResponseTest {
    SpecHelper specHelper = new SpecHelper();
    RedirectResponse redirectResponse;

    @Test
    public void testResponse() throws IOException {
        redirectResponse = new RedirectResponse("/redirect/", "/");
        String response = specHelper.responseString(redirectResponse.response());
        assertTrue(response.contains("302 Found"));
        assertTrue(response.contains("Location:"));
    }
}
