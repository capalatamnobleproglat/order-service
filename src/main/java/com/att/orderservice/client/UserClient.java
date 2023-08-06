package com.att.orderservice.client;

import com.att.orderservice.config.ResilienceConfiguration;
import com.att.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = ResilienceConfiguration.class)
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable("id") Long id);
}
