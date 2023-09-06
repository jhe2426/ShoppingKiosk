package com.shopping.task.service;

import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.response.GetMartTokenResponseDto;
import com.shopping.task.dto.response.GetUserTokenResponseDto;

public interface AuthService {
    public ResponseEntity<? super GetMartTokenResponseDto> getMartToken();
    public ResponseEntity<? super GetUserTokenResponseDto> getUserToken(String userId);
}
