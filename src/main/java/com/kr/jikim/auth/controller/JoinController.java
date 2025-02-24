package com.kr.jikim.auth.controller;

import com.kr.jikim.auth.dto.UserDTO;
import com.kr.jikim.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @GetMapping("/join")
    public String joinPage(){
        return "joinPage";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(UserDTO userDTO){
        userService.join(userDTO);
        return "ok";
    }
}
