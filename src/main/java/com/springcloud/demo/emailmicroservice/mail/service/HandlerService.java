package com.springcloud.demo.emailmicroservice.mail.service;

import com.springcloud.demo.emailmicroservice.client.asks.dto.AskDTO;
import com.springcloud.demo.emailmicroservice.client.booking.dto.BookingDTO;
import com.springcloud.demo.emailmicroservice.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HandlerService {

    private final MailService mailService;

    public void notifyOwnerAboutNewAsk(String askJson) {
        AskDTO askDTO = JsonUtils.fromJson(askJson, AskDTO.class);

        String message = "Tienes una nueva pregunta en una de tus publicaciones:" + askDTO.getRoom().getName() +
                "\n\n" +
                "Pregunta de: " + askDTO.getUser().getName() + " (" + askDTO.getUser().getEmail() + ")\n" +
                "Pregunta: " + askDTO.getQuestion();

        mailService.sendSimpleMail(askDTO.getRoom().getOwner().getEmail(), "Nueva Pregunta", message);
    }

    public void notifyOwnerAboutNewBooking(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        String message = "Se ha creado una nueva reserva en una de tus publicaciones:" + bookingDTO.getRoom().getName() +
                "\n\n" +
                "Reserva de: " + bookingDTO.getUser().getName() + " (" + bookingDTO.getUser().getEmail() + ")\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n";

        mailService.sendSimpleMail(bookingDTO.getRoom().getOwner().getEmail(), "Nueva reserva", message);
    }

    public void notifyOwnerAboutNewReview(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        String message = "Tienes una nueva calificación en una de tus publicaciones:" + bookingDTO.getRoom().getName() +
                "\n\n" +
                "Reserva de: " + bookingDTO.getUser().getName() + " (" + bookingDTO.getUser().getEmail() + ")\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n\n" +
                "Calificación: " + bookingDTO.getRating() + "\n" +
                "Comentario: " + bookingDTO.getReview();

        mailService.sendSimpleMail(bookingDTO.getRoom().getOwner().getEmail(), "Nueva calificación", message);
    }

    public void notifyCustomerAboutBookingReceiptGenerated(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        String message = "Nueva reserva" +
                "\n\n" +
                "Habitación reservada: " + bookingDTO.getRoom().getName() + "\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n";

        try {
            mailService.sendEmailWithAttachment(bookingDTO.getUser().getEmail(), "Comprobante de reserva", message, bookingDTO.getReceiptUrl());
        } catch (Exception e) {
            mailService.sendSimpleMail(bookingDTO.getUser().getEmail(), "Comprobante de reserva", message);
            log.error(e.getMessage());
        }
    }

}
