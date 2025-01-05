package com.techlabs.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Value("$(spring.mail.username)")
    private String fromEmailID;


    public void sendEmail(String recipient, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmailID);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }
}
