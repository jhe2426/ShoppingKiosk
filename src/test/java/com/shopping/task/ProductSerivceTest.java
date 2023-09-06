package com.shopping.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.PostProductRequestDto;
import com.shopping.task.dto.response.GetMartTokenResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.service.AuthService;
import com.shopping.task.service.ProductService;

@SpringBootTest
public class ProductSerivceTest {
    @Autowired
    ProductService productService;
    @Autowired
    AuthService authService;
    @Test
    public void postProduct() {

        AuthToken authToken = new AuthToken("martId", "mart");

        PostProductRequestDto productRequestDto = new PostProductRequestDto(null, null, 0);
        
        ResponseEntity<ResponseDto> responseDto = productService.postProduct(authToken, productRequestDto);

        ResponseDto response = responseDto.getBody();
    
        

    }
}
