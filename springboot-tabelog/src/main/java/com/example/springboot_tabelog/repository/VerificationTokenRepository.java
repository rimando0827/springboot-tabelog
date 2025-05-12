package com.example.springboot_tabelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer> {
    public VerificationToken findByToken(String token);
    void deleteAllByUserId(Integer userId);
}
