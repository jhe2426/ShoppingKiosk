package com.shopping.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.PatchPriceProductRequestDto;
import com.shopping.task.dto.request.PostProductRequestDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.entity.ProductEntity;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.repository.ProductRepository;
import com.shopping.task.service.AuthService;
import com.shopping.task.service.ProductService;

@SpringBootTest
public class ProductSerivceTest {
    @Autowired
    ProductService productService;
    @Autowired
    AuthService authService;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void postProduct() {

        AuthToken authToken = new AuthToken("martId", "mart");

        String inputProdcutName = "사과 한 박스";
        int inputProductPrice = 35000;
        int inputProdcutDeliveryCharge = 3000;

        PostProductRequestDto productRequestDto = new PostProductRequestDto(inputProdcutName, inputProductPrice, inputProdcutDeliveryCharge);
        
        ResponseEntity<ResponseDto> responseDto = productService.postProduct(authToken, productRequestDto);

        ResponseDto response = responseDto.getBody();

        String code = response.getCode();
        String message = response.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);
    }

    @Test
    public void patchPriceProduct() {
        AuthToken authToken = new AuthToken("martId", "mart");

        int inputProductNumber = 4;
        int inputProductPrice = 30000;

        PatchPriceProductRequestDto patchPriceProductRequestDto = new PatchPriceProductRequestDto(inputProductNumber, inputProductPrice);

        ResponseEntity<ResponseDto> responseDto = productService.patchPriceProduct(authToken, patchPriceProductRequestDto);

        ResponseDto response = responseDto.getBody();

        String code = response.getCode();
        String message = response.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);

        ProductEntity productEntity = productRepository.findByProductNumber(inputProductNumber);

        int productPrice = productEntity.getPrice();

        assertEquals(inputProductPrice, productPrice);
    }
}
