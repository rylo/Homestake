import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;

public class ErrorResponseTest {
    private int errorCode;
    private ErrorResponse errorResponse;

    @Test
    public void testConstructor() {
        errorCode = 500;
        errorResponse = new ErrorResponse(errorCode);

        assertEquals(500, errorResponse.errorCode);
        assertEquals("ErrorResponse", errorResponse.getClass().getName());
    }

    @Test
    public void testResponseType() {
        errorCode = 500;
        errorResponse = new ErrorResponse(errorCode);

        assertEquals("java.io.SequenceInputStream", errorResponse.response().getClass().getName());
    }

    @Test
    public void testResponseContents() throws IOException {
        SpecHelper specHelper = new SpecHelper();
        errorResponse = new ErrorResponse(400);
        assertTrue((specHelper.responseString(errorResponse.response())).contains("400"));

        errorResponse = new ErrorResponse(500);
        assertTrue((specHelper.responseString(errorResponse.response())).contains("500"));
    }

}
