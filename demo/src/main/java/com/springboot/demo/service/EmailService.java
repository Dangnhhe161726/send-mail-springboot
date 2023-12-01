package com.springboot.demo.service;

public interface EmailService {
    void sendSimpleMailMessage(String name, String to, String token);
    void sendMimeMessageAttachments(String name, String to, String token);
    void sendMimeMessageEmbeddedImages(String name, String to, String token);
    void sendHtmlEmail(String name, String to, String token);
    void sendHtmlEmailFiles(String name, String to, String token);
}
