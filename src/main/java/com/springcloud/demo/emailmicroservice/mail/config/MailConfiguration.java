package com.springcloud.demo.emailmicroservice.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Value("${email.config.username}")
    private String username;

    @Value("${email.config.password}")
    private String password;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties javaMailProperties = mailSender.getJavaMailProperties();

        javaMailProperties.put("mail.transport.protocol","smtp");
        javaMailProperties.put("mail.smtp.auth","true");
        javaMailProperties.put("mail.smtp.starttls.enable","true");

        return mailSender;
    }
}
