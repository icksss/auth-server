package com.kr.jikim.auth.controller;

import com.kr.jikim.auth.dto.RegisterDTO;
import com.kr.jikim.auth.entity.RegisterEntity;
import com.kr.jikim.auth.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping("/register")
    public String registerPage() {
        System.out.println("registerPage");
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public RegisterEntity register(RegisterDTO dto) {

        return registerService.register(dto);
    }

    @GetMapping("/oauth2/authorize")
    public String getMethodName() {
        return "oauth2/authorize";
    }
    
}
