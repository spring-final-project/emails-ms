package com.springcloud.demo.emailmicroservice.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.config.username}")
    private String username;

    public void sendSimpleMail(String to, String subject, String message){
        if(username == null){
            throw new RuntimeException("username is null");
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, String message, String filePath) throws MessagingException, URISyntaxException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);

        mimeMessageHelper.addAttachment("comprobante.pdf", getFile(filePath));

        javaMailSender.send(mimeMessage);
    }

    InputStreamSource getFile(String filePath) throws URISyntaxException, IOException {
        InputStreamSource attachment;
        URL uri = new URI(filePath).toURL();
        try (InputStream inputStream = uri.openStream()) {
            byte[] fileBytes = inputStream.readAllBytes();
            attachment = new ByteArrayResource(fileBytes);
        }

        return attachment;
    }
}