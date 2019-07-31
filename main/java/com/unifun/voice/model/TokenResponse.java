package com.unifun.voice.model;

import lombok.Data;

@Data
public class TokenResponse {
    private String jwt;
    private String refreshToken;
}
