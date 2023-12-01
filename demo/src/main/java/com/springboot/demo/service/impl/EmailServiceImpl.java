package com.springboot.demo.service.impl;

import com.springboot.demo.service.EmailService;
import com.springboot.demo.utils.EmailUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    public static final String VERIFY_ACCOUNT = "Verify Account";
    public static final String UTF_8_ENCODING = "UTF-8";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;
    private final ResourceLoader resourceLoader;

    @Override
    @Async
    public void sendSimpleMailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(VERIFY_ACCOUNT);
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(simpleMailMessage);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageAttachments(String name, String to, String token) {
        try {
            MimeMessage mimeMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true , UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(VERIFY_ACCOUNT);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(EmailUtils.getEmailMessage(name, host, token));
            //Add attachments
            // Load resources using ResourceLoader
            Resource image1Resource = resourceLoader.getResource("classpath:static/image_test_send_mail/image_1.jpg");
            Resource image2Resource = resourceLoader.getResource("classpath:static/image_test_send_mail/image_2.jpg");

            // Convert Resource to File
            File image1File = image1Resource.getFile();
            File image2File = image2Resource.getFile();

            FileSystemResource image1 = new FileSystemResource(image1File);
            FileSystemResource image2 = new FileSystemResource(image2File);

            helper.addAttachment(image1.getFilename(), image1);
            helper.addAttachment(image2.getFilename(), image2);
            emailSender.send(mimeMessage);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    @Async
    public void sendMimeMessageEmbeddedImages(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendMimeMessageEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmail(String name, String to, String token) {

    }

    @Override
    @Async
    public void sendHtmlEmailFiles(String name, String to, String token) {

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}
