package com.jlog.api.response;

import lombok.Getter;

@Getter
public class SessionResponse {

    private final String accessToken;
    private final String userName;

    public SessionResponse(String accessToken, String userName) {
        this.accessToken = accessToken;
        this.userName = userName;
    }
}
