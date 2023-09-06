package com.shopping.task.service.implement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shopping.task.common.constant.ResponseMessage;
import com.shopping.task.dto.response.GetMartTokenResponseDto;
import com.shopping.task.dto.response.GetUserTokenResponseDto;
import com.shopping.task.provider.JwtProvider;
import com.shopping.task.repository.UserRepository;
import com.shopping.task.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImplement implements AuthService{

    private final UserRepository userRepository;

    @Value("${admin.id}")
    String adminId;

    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<? super GetMartTokenResponseDto> getMartToken() {
        GetMartTokenResponseDto body = null;

        String role = "mart";
        String jwt = jwtProvider.create(adminId, role);

        body = new GetMartTokenResponseDto(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetUserTokenResponseDto> getUserToken(String userId) {

        boolean notExistsUserID = userId.isBlank();
        
        if(notExistsUserID) return ResponseMessage.VAILDATION_FAILED;

        
        GetUserTokenResponseDto body = null;

        try {

            boolean existsID = userRepository.existsById(userId);

            if(!existsID) return ResponseMessage.NOT_EXIST_USER_ID;
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        String role = "user";
        String jwt = jwtProvider.create(userId, role);

        body = new GetUserTokenResponseDto(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    
}
