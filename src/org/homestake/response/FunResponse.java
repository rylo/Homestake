package org.homestake.response;

import org.homestake.utils.Decoder;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FunResponse extends ServerResponse {

    @Override
    public void setBody() throws Exception {
        setResponseBody("<form action='/fun/'>Clean up my language: <input type='text' name='dirty'></form><h1>" + cleanUp(Decoder.decode(getQueryString("dirty"))) + "</h1>");
        this.body = new ByteArrayInputStream(responseBody.getBytes());
    }

    @Override
    public HashMap<String, Object> headerValues() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("status", 200);
        headers.put("content-type", "text/html");
        headers.put("content-length", new Long(responseBody.length()));
        return headers;
    }

    public String cleanUp(String dirtySentence) {
        for (String word : dirtyWords()) {
            dirtySentence = dirtySentence.replace(word, randomWord(cleanWords()));
        }
        return dirtySentence;
    }

    public ArrayList<String> dirtyWords() {
        ArrayList<String> wordList = new ArrayList<String>();
            wordList.add("shit");
            wordList.add("fuck");
            wordList.add("ass");
        return wordList;
    }

    public String randomWord(ArrayList<String> wordList) {
        Random randomGenerator = new Random();
        return wordList.get(randomGenerator.nextInt(wordList.size()));
    }

    public ArrayList<String> cleanWords() {
        ArrayList<String> wordList = new ArrayList<String>();
            wordList.add("rainbow");
            wordList.add("butterfly");
            wordList.add("sparkle");
        return wordList;
    }

    public String getQueryString(String name) {
        return (String) requestParser.queryStrings().get(name);
    }

}
