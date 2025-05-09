package com.example.springboot_tabelog.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.springboot_tabelog.entity.User;
import com.example.springboot_tabelog.service.VerificationTokenService;

@Component
public class ResetEventListener {
	private final VerificationTokenService verificationTokenService;    
    private final JavaMailSender javaMailSender;
    
    public ResetEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;        
        this.javaMailSender = mailSender;
    }
    
    @EventListener
    private void resetEvent(ResetEvent resetEvent) {
        User user = resetEvent.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.create(user, token);
        
        String senderAddress = "springboot.samuraitravel@example.com";
        String recipientAddress = user.getEmail();
        String subject = "メール承認";
        String confirmationUrl = resetEvent.getRequestUrl() + "?token=" + token;
        String message = "以下のリンクをクリックしてパスワードリセットをしてください";
        
        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\n" + confirmationUrl);
        javaMailSender.send(mailMessage);

    }

}