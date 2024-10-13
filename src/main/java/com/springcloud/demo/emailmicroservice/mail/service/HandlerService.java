package com.springcloud.demo.emailmicroservice.mail.service;

import com.springcloud.demo.emailmicroservice.client.asks.dto.AskDTO;
import com.springcloud.demo.emailmicroservice.client.booking.dto.BookingDTO;
import com.springcloud.demo.emailmicroservice.client.rooms.RoomClient;
import com.springcloud.demo.emailmicroservice.client.rooms.dto.RoomDTO;
import com.springcloud.demo.emailmicroservice.client.users.UserClient;
import com.springcloud.demo.emailmicroservice.client.users.dto.UserDTO;
import com.springcloud.demo.emailmicroservice.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HandlerService {

    private final MailService mailService;
    private final RoomClient roomClient;
    private final UserClient userClient;

    public void notifyOwnerAboutNewAsk(String askJson) {
        AskDTO askDTO = JsonUtils.fromJson(askJson, AskDTO.class);
        String roomId = askDTO.getRoomId();
        String userId = askDTO.getUserId();

        RoomDTO room = roomClient.findById(roomId);
        UserDTO owner = userClient.findById(room.getOwnerId().toString());
        UserDTO askBy = userClient.findById(userId);

        String message = "Tienes una nueva pregunta en una de tus publicaciones:" + room.getName() +
                "\n\n" +
                "Pregunta de: " + askBy.getName() + " (" + askBy.getEmail() + ")\n" +
                "Pregunta: " + askDTO.getQuestion();

        mailService.sendSimpleMail(owner.getEmail(), "Nueva Pregunta", message);
    }

    public void notifyOwnerAboutNewBooking(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        RoomDTO room = roomClient.findById(bookingDTO.getRoomId());
        UserDTO owner = userClient.findById(room.getOwnerId().toString());
        UserDTO bookedBy = userClient.findById(bookingDTO.getUserId());

        String message = "Se ha creado una nueva reserva en una de tus publicaciones:" + room.getName() +
                "\n\n" +
                "Reserva de: " + bookedBy.getName() + " (" + bookedBy.getEmail() + ")\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n";

        mailService.sendSimpleMail(owner.getEmail(), "Nueva reserva", message);
    }

    public void notifyOwnerAboutNewReview(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        RoomDTO room = roomClient.findById(bookingDTO.getRoomId());
        UserDTO owner = userClient.findById(room.getOwnerId().toString());
        UserDTO bookedBy = userClient.findById(bookingDTO.getUserId());

        String message = "Tienes una nueva calificación en una de tus publicaciones:" + room.getName() +
                "\n\n" +
                "Reserva de: " + bookedBy.getName() + " (" + bookedBy.getEmail() + ")\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n\n" +
                "Calificación: " + bookingDTO.getRating() + "\n" +
                "Comentario: " + bookingDTO.getReview();

        mailService.sendSimpleMail(owner.getEmail(), "Nueva calificación", message);
    }

    public void notifyCustomerAboutBookingReceiptGenerated(String bookingJson) {
        BookingDTO bookingDTO = JsonUtils.fromJson(bookingJson, BookingDTO.class);

        RoomDTO room = roomClient.findById(bookingDTO.getRoomId());
        UserDTO user = userClient.findById(bookingDTO.getUserId());

        String message = "Nueva reserva" +
                "\n\n" +
                "Habitación reservada: " + room.getName() + "\n" +
                "Fecha de entrada: " + bookingDTO.getCheckIn() + "\n" +
                "Fecha de salida: " + bookingDTO.getCheckOut() + "\n";

        try {
            mailService.sendEmailWithAttachment(user.getEmail(), "Comprobante de reserva", message, bookingDTO.getReceiptUrl());
        } catch (Exception e) {
            mailService.sendSimpleMail(user.getEmail(), "Comprobante de reserva", message);
            log.error(e.getMessage());
        }
    }

}
