package com.springcloud.demo.emailmicroservice.client.asks.dto;

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
public class AskDTO {
    private String id;
    private String createdAt;
    private String respondedAt;
    private String question;
    private RoomDTO room;
    private UserDTO user;
}
