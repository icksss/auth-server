package com.kr.jikim.auth.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String clientName;
    private String clientSecret;
    private String redirectUris;
    private String postLogoutRedirectUris;
    private String scopes;
}
