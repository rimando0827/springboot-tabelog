package com.example.springboot_tabelog.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.service.VerificationLoginTokenService;


@Component
public class SignupEventListener {
	  private final VerificationLoginTokenService verificationLoginTokenService;    
	    private final JavaMailSender javaMailSender;
	    
	    public SignupEventListener(VerificationLoginTokenService verificationLoginTokenService, JavaMailSender mailSender) {
	        this.verificationLoginTokenService = verificationLoginTokenService;        
	        this.javaMailSender = mailSender;
	    }

	    @EventListener
	    private void onSignupEvent(SignupEvent signupEvent) {
	        User user = signupEvent.getUser();
	        String token = UUID.randomUUID().toString();
	        verificationLoginTokenService.create(user, token);
	        
	        String senderAddress = "springboot.samuraitravel@example.com";
	        String recipientAddress = user.getEmail();
	        String subject = "メール認証";
	        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
	        String message = "以下のリンクをクリックして会員登録を完了してください。";
	        
	        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
	        mailMessage.setFrom(senderAddress);
	        mailMessage.setTo(recipientAddress);
	        mailMessage.setSubject(subject);
	        mailMessage.setText(message + "\n" + confirmationUrl);
	        javaMailSender.send(mailMessage);
	    }
}
