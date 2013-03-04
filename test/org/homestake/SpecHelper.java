package org.homestake;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SpecHelper {

    public String responseString(InputStream inputStream) throws IOException {
        InputStreamReader input = new InputStreamReader(inputStream);
        int integer;
        char character;
        String response = "";

        while((integer = input.read()) != -1) {
            character = (char) integer;
            response += String.valueOf(character);
        }
        return response;
    }

}
