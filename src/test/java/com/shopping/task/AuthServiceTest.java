package com.shopping.task;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.response.GetMartTokenResponseDto;
import com.shopping.task.dto.response.GetUserTokenResponseDto;
import com.shopping.task.service.AuthService;


@SpringBootTest
public class AuthServiceTest {


    @Autowired
    AuthService authService;

    @Test
    public void getMartToken() {
       ResponseEntity<? super GetMartTokenResponseDto> response = authService.getMartToken();

       GetMartTokenResponseDto getMartTokenResponseDto = (GetMartTokenResponseDto)response.getBody();
       String code = getMartTokenResponseDto.getCode();
       String message = getMartTokenResponseDto.getMessage();
       String token = getMartTokenResponseDto.getToken();

       assertEquals("SU", code);
       assertEquals("Success", message);
       
       assertNotNull(token);
    }

    @Test
    public void getUserToken() {
        String userId = "jhe2426";
        ResponseEntity<? super GetUserTokenResponseDto> response = authService.getUserToken(userId);

        GetUserTokenResponseDto getUserTokenResponseDto = (GetUserTokenResponseDto)response.getBody();
        String code = getUserTokenResponseDto.getCode();
        String message = getUserTokenResponseDto.getMessage();
        String token = getUserTokenResponseDto.getToken();
        
        assertEquals("SU", code);
        assertEquals("Success", message);

        assertNotNull(token);

    }
}
