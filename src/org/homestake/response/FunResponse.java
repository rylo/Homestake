package org.homestake.response;

import org.homestake.utils.Decoder;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FunResponse extends ServerResponse {

    @Override
    public void setBody() throws Exception {
        String cleanedUpSentence = "";
        if (getQueryString("sentence") != null) cleanedUpSentence = cleanUp(Decoder.decode(getQueryString("sentence")));

        setResponseBody("<form action='/fun/'>Clean up my language: <input type='text' name='sentence'></form><h1>" + cleanedUpSentence + "</h1>");
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
        StringBuilder restoredSentence = new StringBuilder();
        String[] choppedSentence = dirtySentence.split(" ");

        for (String word : choppedSentence) {
            if (word.length() == 4) {
                restoredSentence.append(" " + randomWord(cleanWords()));
            } else {
                restoredSentence.append(" " + word);
            }
        }
        return restoredSentence.toString();
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
            wordList.add("cupcake");
            wordList.add("kitty");
            wordList.add("gumdrop");
            wordList.add("sunshine");
        return wordList;
    }

    public String getQueryString(String name) {
        return (String) requestParser.queryStrings().get(name);
    }

}
