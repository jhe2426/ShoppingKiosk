package com.shopping.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserTokenResponseDto extends ResponseDto {
    private String token;
    private int expirationDate;

  
    public GetUserTokenResponseDto(String token) {
        super("SU", "Success");
        this.token = token;
        this.expirationDate = 3600;
    }
}
