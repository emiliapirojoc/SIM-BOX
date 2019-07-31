package com.unifun.voice.model;

import lombok.Data;

@Data
public class ResponseLogout {
    private String user;
    private String jwt;
    private String refreshToken;
}
