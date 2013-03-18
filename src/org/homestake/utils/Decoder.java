package org.homestake.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Decoder {

    public static String decode(String string) throws UnsupportedEncodingException {
        return URLDecoder.decode(string, "UTF-8");
    }

}
