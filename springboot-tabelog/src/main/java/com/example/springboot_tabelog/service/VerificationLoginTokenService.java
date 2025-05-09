package com.example.springboot_tabelog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.entity.VerificationLoginToken;
import com.example.springboot_tabelog.repository.VerificationLoginTokenRepository;
@Service
public class VerificationLoginTokenService {
	  private final VerificationLoginTokenRepository verificationLoginTokenRepository;
	    
	    
	    public VerificationLoginTokenService(VerificationLoginTokenRepository verificationLoginTokenRepository) {        
	        this.verificationLoginTokenRepository = verificationLoginTokenRepository;
	    } 
	    
	    @Transactional
	    public void create(User user, String token) {
	        VerificationLoginToken verificationLoginToken = new VerificationLoginToken();
	        
	        verificationLoginToken.setUser(user);
	        verificationLoginToken.setToken(token);        
	        
	        verificationLoginTokenRepository.save(verificationLoginToken);
	    }    
	    
	    // トークンの文字列で検索した結果を返す
	    public VerificationLoginToken getVerificationLoginToken(String token) {
	        return verificationLoginTokenRepository.findByToken(token);
	    }    

}
