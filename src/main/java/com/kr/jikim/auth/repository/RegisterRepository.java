package com.kr.jikim.auth.repository;

import com.kr.jikim.auth.entity.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<RegisterEntity, String> {
    Optional<RegisterEntity> findByClientId(String id);
}
