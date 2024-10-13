package com.springcloud.demo.emailmicroservice.client.rooms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.demo.emailmicroservice.client.rooms.dto.RoomDTO;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoomClientImpl implements RoomClient {

    private final RoomClient roomClient;

    @Override
    @CircuitBreaker(name = "rooms-service", fallbackMethod = "findRoomByIdFallback")
    public RoomDTO findById(String id) {
        return roomClient.findById(id);
    }

    RoomDTO findRoomByIdFallback(String id, Throwable e) throws Exception {
        if(!(e instanceof FeignException.FeignClientException feignClientException)){
            log.error("Rooms service not available. Try later");
        } else {

            Map body = new ObjectMapper().readValue(feignClientException.contentUTF8(), Map.class);

            if(feignClientException.status() == 404) {
                log.error((String) body.get("message"));
            }

        }

        log.error(e.getMessage());
        throw new RuntimeException(e.getMessage());

    }
}
