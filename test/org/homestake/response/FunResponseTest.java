package org.homestake.response;

import org.homestake.utils.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

public class FunResponseTest {
    String request = "GET /fun/?sentence=lol HTTP/1.1\n";
    RequestParser parsedRequest = new RequestParser(request);
    FunResponse response = new FunResponse();
    HashMap<String, Object> headers;

    @Before
    public void test() throws Exception {
        response.setRequestParser(parsedRequest);
        response.setBody();
        headers = response.headerValues();
    }

    @Test
    public void getQueryString() {
        assertEquals("lol", response.getQueryString("sentence"));
    }

    @Test
    public void cleanWords() {
        ArrayList<String> wordList = response.cleanWords();
        assertTrue(wordList.contains("rainbow"));
        assertTrue(wordList.contains("butterfly"));
        assertTrue(wordList.contains("sparkle"));
    }

    @Test
    public void cleansUpLanguage() {
        assertNotSame("shiz", response.cleanUp("shiz"));
        assertNotSame("duck", response.cleanUp("duck"));
    }

    @Test
    public void randomWord() {
        ArrayList<String> wordList = new ArrayList<String>();
            wordList.add("harp");
            wordList.add("cello");
        String randomWord = response.randomWord(wordList);
        assertTrue(randomWord == "harp" || randomWord == "cello");
    }

    @Test
    public void headerValues() {
        assertEquals(200, headers.get("status"));
        assertEquals("text/html", headers.get("content-type"));
        assertEquals(new Long(response.responseBody.length()), headers.get("content-length"));
    }

}
