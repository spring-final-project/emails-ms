package com.springcloud.demo.emailmicroservice.service;

import com.springcloud.demo.emailmicroservice.mail.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailService mailService;

    private final String username = "gonzalo.jerezn@gmail.com";

    @BeforeEach
    void setup(){
        ReflectionTestUtils.setField(mailService, "username", username);
    }

    @Test
    void sendSimpleMail() {
        willDoNothing().given(javaMailSender).send(any(SimpleMailMessage.class));

        mailService.sendSimpleMail("gonzalo.jerezn@gmail.com", "Prueba", "Esto es una prueba");

        verify(javaMailSender).send(any(SimpleMailMessage.class));
        verify(javaMailSender).send(argThat((SimpleMailMessage args) ->
                Objects.equals(args.getFrom(), username) &&
                        (args.getTo() != null && args.getTo()[0].equals("gonzalo.jerezn@gmail.com")) &&
                        Objects.equals(args.getSubject(), "Prueba") &&
                        Objects.equals(args.getText(), "Esto es una prueba")
        ));
    }

    @Test
    void sendEmailWithAttachment() throws MessagingException, URISyntaxException, IOException {
        given(javaMailSender.createMimeMessage()).willReturn(mock(MimeMessage.class));
        willDoNothing().given(javaMailSender).send(any(MimeMessage.class));

        mailService.sendEmailWithAttachment(
                "gonzalo.jerezn@gmail.com",
                "Prueba",
                "Esto es una prueba",
                "https://docs.spring.io/spring-framework/reference/_/img/spring-logo.svg"
        );

        verify(javaMailSender).send(any(MimeMessage.class));
    }

}