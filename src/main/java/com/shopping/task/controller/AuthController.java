package com.shopping.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.task.common.constant.RequestPattern;
import com.shopping.task.dto.response.GetMartTokenResponseDto;
import com.shopping.task.dto.response.GetUserTokenResponseDto;
import com.shopping.task.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(RequestPattern.AUTH_API)
public class AuthController {

    private final AuthService authService;
    
    private final String GET_MART_TOKEN = "mart";
    private final String GET_USER_TOKEN = "user/{userId}";

    @GetMapping(GET_MART_TOKEN)
    public ResponseEntity<? super GetMartTokenResponseDto> getMartToken() {

        ResponseEntity<? super GetMartTokenResponseDto> response = authService.getMartToken();

        return response;
    }


    @GetMapping(GET_USER_TOKEN)
    public ResponseEntity<? super GetUserTokenResponseDto> getUserToken(
        @PathVariable("userId") String userId
    ) {
        ResponseEntity<? super GetUserTokenResponseDto> response = authService.getUserToken(userId);
        return response;
    }

    
}
