package com.springcloud.demo.emailmicroservice.client.asks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AskDTO {
    private String id;
    private String createdAt;
    private String respondedAt;
    private String question;
    private String roomId;
    private String userId;
}
