package com.springcloud.demo.emailmicroservice.client.booking.dto;

import com.springcloud.demo.emailmicroservice.client.rooms.dto.RoomDTO;
import com.springcloud.demo.emailmicroservice.client.users.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String id;
    private String receiptUrl;
    private String checkIn;
    private String checkOut;
    private Integer rating;
    private String review;
    private UserDTO user;
    private RoomDTO room;
}
