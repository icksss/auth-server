package com.kr.jikim.auth.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String role;
    private String nickname;
    private String phone;
}
