package com.springcloud.demo.emailmicroservice.client.booking.dto;

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
    private String userId;
    private String roomId;
    private Integer rating;
    private String review;
}
