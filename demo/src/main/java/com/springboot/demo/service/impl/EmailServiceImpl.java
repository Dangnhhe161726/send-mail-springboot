package com.springboot.demo.service.impl;

import com.springboot.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;
    @Override
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("New User Account Verification");
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText("hello this is my email");
            emailSender.send(simpleMailMessage);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void sendMimeMessageAttachments(String name, String to, String token) {

    }

    @Override
    public void sendMimeMessageEmbeddedImages(String name, String to, String token) {

    }

    @Override
    public void sendMimeMessageEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmail(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmailFiles(String name, String to, String token) {

    }
}
