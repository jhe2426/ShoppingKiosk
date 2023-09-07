package com.shopping.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.CouponApplicationOrdersInformation;
import com.shopping.task.dto.request.PostCouponApplicationOrdersRequestDto;
import com.shopping.task.dto.request.PostProductOrdersRequestDto;
import com.shopping.task.dto.request.ProductOrdersInformation;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.entity.OrdersEntity;
import com.shopping.task.entity.OrdersProductEntity;
import com.shopping.task.entity.UserEntity;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.repository.OrdersProductRepository;
import com.shopping.task.repository.OrdersRepository;
import com.shopping.task.repository.UserRepository;
import com.shopping.task.service.AuthService;
import com.shopping.task.service.OrdersService;


@SpringBootTest
public class OrdersSerivceTest {
    @Autowired
    OrdersService ordersService ;
    @Autowired
    AuthService authService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrdersProductRepository ordersProductRepository;


    @Test
    public void postOrders() {

        String userId = "jhe2426";

        AuthToken authToken = new AuthToken(userId, "user");

        List<ProductOrdersInformation> productOrdersInformationList = new ArrayList<>();

        int inputProductNumberOne = 20;
        int inputProductQuantityOne = 2;

        
        int inputProductNumberTwo = 21;
        int inputProductQuantityTwo = 1;

        ProductOrdersInformation productOrdersInformationOne = new ProductOrdersInformation(inputProductNumberOne, inputProductQuantityOne);
        ProductOrdersInformation productOrdersInformationTwo = new ProductOrdersInformation(inputProductNumberTwo, inputProductQuantityTwo);
    

        productOrdersInformationList.add(productOrdersInformationOne);
        productOrdersInformationList.add(productOrdersInformationTwo);

        PostProductOrdersRequestDto postProductOrdersRequestDto = new PostProductOrdersRequestDto(productOrdersInformationList);

        ResponseEntity<ResponseDto> response = ordersService.postProductOrders(authToken, postProductOrdersRequestDto);

        ResponseDto responseDto = response.getBody();

        String code = responseDto.getCode();
        String message = responseDto.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);

        UserEntity userEntity = userRepository.findById(userId);

        int orderNumber = ordersRepository.getRecentOrdersNumber(userEntity);

        OrdersEntity ordersEntity = ordersRepository.findByOrdersNumber(orderNumber);

        List<OrdersProductEntity> ordersProductEntities = ordersProductRepository.findByOrdersEntity(ordersEntity);

        int ordersProductNumberOne = ordersProductEntities.get(0).getProductNumber();
        int ordersProductQuantityOne = ordersProductEntities.get(0).getProductQuantity();

        int ordersProductNumberTwo = ordersProductEntities.get(1).getProductNumber();
        int ordersProductQuantityTwo = ordersProductEntities.get(1).getProductQuantity();


        assertEquals(inputProductNumberOne, ordersProductNumberOne);
        assertEquals(inputProductQuantityOne, ordersProductQuantityOne);

        assertEquals(inputProductNumberTwo, ordersProductNumberTwo);
        assertEquals(inputProductQuantityTwo, ordersProductQuantityTwo);

    }


    @Test
    public void postCouponApplicationOrders() {

        String userId = "jhe2426";

        AuthToken authToken = new AuthToken(userId, "user");

        

        int inputProductNumberOne = 20;
        int inputProductQuantityOne = 2;

        
        int inputProductNumberTwo = 21;
        int inputProductQuantityTwo = 1;

        int inputCouponNumber = 7;

        CouponApplicationOrdersInformation couponApplicationOrdersInformationOne 
            = new CouponApplicationOrdersInformation(inputProductNumberOne, inputProductQuantityOne);

        CouponApplicationOrdersInformation couponApplicationOrdersInformationTwo
            = new CouponApplicationOrdersInformation(inputProductNumberTwo, inputProductQuantityTwo);

        List<CouponApplicationOrdersInformation> productOrdersInformationList = new ArrayList<>();

        productOrdersInformationList.add(couponApplicationOrdersInformationOne);
        productOrdersInformationList.add(couponApplicationOrdersInformationTwo);

        PostCouponApplicationOrdersRequestDto postCouponApplicationOrdersRequestDto 
            = new PostCouponApplicationOrdersRequestDto(productOrdersInformationList, inputCouponNumber);


        ResponseEntity<ResponseDto> response 
            = ordersService.postCouponApplicationOrders(authToken, postCouponApplicationOrdersRequestDto);

        ResponseDto responseDto = (ResponseDto)response.getBody();


        String code = responseDto.getCode();
        String message = responseDto.getMessage();
    
        assertEquals("SU", code);
        assertEquals("Success", message);


        UserEntity userEntity = userRepository.findById(userId);

        int orderNumber = ordersRepository.getRecentOrdersNumber(userEntity);

        OrdersEntity ordersEntity = ordersRepository.findByOrdersNumber(orderNumber);

        List<OrdersProductEntity> ordersProductEntities = ordersProductRepository.findByOrdersEntity(ordersEntity);

        int ordersProductNumberOne = ordersProductEntities.get(0).getProductNumber();
        int ordersProductQuantityOne = ordersProductEntities.get(0).getProductQuantity();

        int ordersProductNumberTwo = ordersProductEntities.get(1).getProductNumber();
        int ordersProductQuantityTwo = ordersProductEntities.get(1).getProductQuantity();

        int ordersCouponNumber = ordersEntity.getCouponPkNumber();

        assertEquals(inputCouponNumber, ordersCouponNumber);

        assertEquals(inputProductNumberOne, ordersProductNumberOne);
        assertEquals(inputProductQuantityOne, ordersProductQuantityOne);

        assertEquals(inputProductNumberTwo, ordersProductNumberTwo);
        assertEquals(inputProductQuantityTwo, ordersProductQuantityTwo);

    }


  
}
