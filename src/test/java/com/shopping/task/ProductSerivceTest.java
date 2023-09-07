package com.shopping.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.GetParticularDateProductPriceRequestDto;
import com.shopping.task.dto.request.PatchPriceProductRequestDto;
import com.shopping.task.dto.request.PostProductRequestDto;
import com.shopping.task.dto.response.GetParticularDateProductPriceResponseDto;
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
        
        ResponseEntity<ResponseDto> response = productService.postProduct(authToken, productRequestDto);

        ResponseDto responseDto = response.getBody();

        String code = responseDto.getCode();
        String message = responseDto.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);
    }

    @Test
    public void patchPriceProduct() {
        AuthToken authToken = new AuthToken("martId", "mart");

        int inputProductNumber = 1;
        int inputProductPrice = 40000;

        PatchPriceProductRequestDto patchPriceProductRequestDto = new PatchPriceProductRequestDto(inputProductNumber, inputProductPrice);

        ResponseEntity<ResponseDto> response = productService.patchPriceProduct(authToken, patchPriceProductRequestDto);

        ResponseDto responseDto = response.getBody();

        String code = responseDto.getCode();
        String message = responseDto.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);

        ProductEntity productEntity = productRepository.findByProductNumber(inputProductNumber);

        int productPrice = productEntity.getPrice();

        assertEquals(inputProductPrice, productPrice);
    }

    @Test
    public void deleteProduct() {
        AuthToken authToken = new AuthToken("martId", "mart");

        Integer productNumber = 1;
        
        ResponseEntity<ResponseDto> response = productService.deleteProduct(authToken, productNumber);

        ResponseDto responseDto = response.getBody();

        String code = responseDto.getCode();
        String message = responseDto.getMessage();

        assertEquals("SU", code);
        assertEquals("Success", message);

        ProductEntity productEntity = productRepository.findByProductNumber(productNumber);

        assertNull(productEntity);
    }

    @Test
    public void getParticularDateProductPrice()  {
        AuthToken authToken = new AuthToken("martId", "mart");

        String date = "2023-09-03 19:22:15";
        int prodcutNumber = 1;
        GetParticularDateProductPriceRequestDto requestDto = new GetParticularDateProductPriceRequestDto(date, prodcutNumber);

        ResponseEntity<? super GetParticularDateProductPriceResponseDto> response 
            = productService.getParticularDateProductPrice(authToken, requestDto);

        GetParticularDateProductPriceResponseDto responseDto = (GetParticularDateProductPriceResponseDto)response.getBody();

        String code = responseDto.getCode();
        String message = responseDto.getMessage();

        assertEquals("SU", code);
        assertEquals("Success", message);

    }
}
