package com.example.springboot_tabelog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_tabelog.entity.VerificationLoginToken;

public interface VerificationLoginTokenRepository extends JpaRepository< VerificationLoginToken, Integer> {
	  public VerificationLoginToken findByToken(String token);

}
