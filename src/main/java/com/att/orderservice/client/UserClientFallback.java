package com.att.orderservice.client;

import com.att.orderservice.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserClientFallback implements UserClient {
    @Override
    public UserDto getUser(Long id) {
        UserDto fallbackUser = new UserDto();
        fallbackUser.setId(0L);
        fallbackUser.setUsername("userName");
        fallbackUser.setEmail("default@example.com");

        return fallbackUser;
    }
}
