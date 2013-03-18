package org.homestake.utils;

import org.homestake.response.ServerResponse;

public class RegisteredRoute {
    public String matcher;
    public ServerResponse response;

    public RegisteredRoute(String matcher, ServerResponse response) {
        this.matcher = matcher;
        this.response = response;
    }

}
