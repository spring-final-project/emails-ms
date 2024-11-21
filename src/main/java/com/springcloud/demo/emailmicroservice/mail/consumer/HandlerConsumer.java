package com.springcloud.demo.emailmicroservice.mail.consumer;

import com.springcloud.demo.emailmicroservice.mail.service.HandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class HandlerConsumer {

    private final HandlerService handlerService;

    /**
     * Work in local, for development
     * It will be ignored in production
     */
    @KafkaListener(topics = "${spring.kafka.topics.ASK_CREATED_TOPIC}")
    public void handleNewAsk(String ask) {
        handlerService.notifyOwnerAboutNewAsk(ask);
    }

    @KafkaListener(topics = "${spring.kafka.topics.BOOKING_CREATED_TOPIC}")
    public void handleNewBooking(String booking) {
        handlerService.notifyOwnerAboutNewBooking(booking);
    }

    @KafkaListener(topics = "${spring.kafka.topics.BOOKING_RECEIPT_GENERATED_TOPIC}")
    public void handleBookingReceiptGenerated(String booking) {
        handlerService.notifyCustomerAboutBookingReceiptGenerated(booking);
    }

    @KafkaListener(topics = "${spring.kafka.topics.REVIEW_CREATED_TOPIC}")
    public void handleReviewCreated(String booking) {
        handlerService.notifyOwnerAboutNewReview(booking);
    }

    /**
     * Work un lambda environment
     * It will be ignored in local
     */
    @Bean
    public Function<String, String> handleNewAskServerless() {
        return message -> {
            return message;
        };
    }
}
