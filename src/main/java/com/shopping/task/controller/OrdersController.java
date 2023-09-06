package com.shopping.task.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.task.common.constant.RequestPattern;
import com.shopping.task.dto.request.GetAvailableCouponListRequestDto;
import com.shopping.task.dto.request.PostCouponApplicationOrdersRequestDto;
import com.shopping.task.dto.request.PostProductOrdersRequestDto;
import com.shopping.task.dto.response.GetAvailableCouponListResponseDto;
import com.shopping.task.dto.response.GetOrdersResponseDto;
import com.shopping.task.dto.response.GetRecentOrdersNumberResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.service.OrdersService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(RequestPattern.ORDERS_API)
public class OrdersController {

    private final OrdersService ordersService;

    private final String POST_PRODCUT_ORDERS = "";
    private final String POST_COUPON_APPLICATION_PRODUCT_ORDERS = "application-coupon";
    private final String GET_AVAILABLE_COUPON_LIST = "available-coupon/list";
    private final String GET_ORDERS = "{ordersNumber}";
    private final String GET_RECENT_ORDERS_NUMBER = "recent-orders-number";


    @PostMapping(POST_PRODCUT_ORDERS)
    public ResponseEntity<ResponseDto> postProductOrders(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody PostProductOrdersRequestDto request
    ) {
        ResponseEntity<ResponseDto> response = ordersService.postProductOrders(authToken, request);
        return response;
    }

    @PostMapping(POST_COUPON_APPLICATION_PRODUCT_ORDERS)
    public ResponseEntity<ResponseDto> postCouponApplicationProductOrders(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody PostCouponApplicationOrdersRequestDto request
    ) {
        ResponseEntity<ResponseDto> response = ordersService.postCouponApplicationOrders(authToken, request);
        return response;
    }

    @GetMapping(GET_AVAILABLE_COUPON_LIST)
    public ResponseEntity<? super GetAvailableCouponListResponseDto> getAvailableCouponList(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody GetAvailableCouponListRequestDto request
    ) {
        ResponseEntity<? super GetAvailableCouponListResponseDto> response = ordersService.getAvailableCouponList(authToken, request);
        return response;
    }

    @GetMapping(GET_ORDERS)
    public ResponseEntity<? super GetOrdersResponseDto> getOrdersList(
        @AuthenticationPrincipal AuthToken authToken,
        @PathVariable("ordersNumber") Integer ordersNumber
    ) {
        ResponseEntity<? super GetOrdersResponseDto> response = ordersService.getOrders(authToken, ordersNumber);
        return response;
    }
    
    @GetMapping(GET_RECENT_ORDERS_NUMBER)
    public ResponseEntity<? super GetRecentOrdersNumberResponseDto> getRecentOrdersNumber (
        @AuthenticationPrincipal AuthToken authToken
    ) {
        ResponseEntity<? super GetRecentOrdersNumberResponseDto> response = ordersService.getRecentOrdersNumber(authToken);
        return response;
    }   
}
