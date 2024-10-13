package com.springcloud.demo.emailmicroservice.client.rooms;

import com.springcloud.demo.emailmicroservice.client.rooms.dto.RoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rooms-ms", url = "${client.rooms-ms.url}")
public interface RoomClient {

    @GetMapping("/api/rooms/{id}")
    RoomDTO findById(@PathVariable String id);
}
