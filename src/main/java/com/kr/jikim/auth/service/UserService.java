package com.kr.jikim.auth.service;

import com.kr.jikim.auth.dto.UserDTO;
import com.kr.jikim.auth.entity.UserEntity;
import com.kr.jikim.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(UserDTO userDTO){
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole("ADMIN");
        user.setNickname(userDTO.getNickname());
        user.setPhone(userDTO.getPhone());

        userRepository.save(user);
    }
}
