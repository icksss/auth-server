package com.kr.jikim.auth.repository;

import com.kr.jikim.auth.entity.ConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsentRepository extends JpaRepository<ConsentEntity, String> {
    Optional<ConsentEntity> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
