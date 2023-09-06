package com.shopping.task.service;

import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.GetAvailableCouponListRequestDto;
import com.shopping.task.dto.request.PostCouponApplicationOrdersRequestDto;
import com.shopping.task.dto.request.PostProductOrdersRequestDto;
import com.shopping.task.dto.response.GetAvailableCouponListResponseDto;
import com.shopping.task.dto.response.GetOrdersResponseDto;
import com.shopping.task.dto.response.GetRecentOrdersNumberResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.provider.AuthToken;

public interface OrdersService {
    public ResponseEntity<ResponseDto> postProductOrders(AuthToken authToken, PostProductOrdersRequestDto dto);
    public ResponseEntity<ResponseDto> postCouponApplicationOrders(AuthToken authToken, PostCouponApplicationOrdersRequestDto dto);
    public ResponseEntity<? super GetAvailableCouponListResponseDto> getAvailableCouponList(AuthToken authToken, GetAvailableCouponListRequestDto dto);
    public ResponseEntity<? super GetOrdersResponseDto> getOrders(AuthToken authToken, Integer getOrders);
    public ResponseEntity<? super GetRecentOrdersNumberResponseDto> getRecentOrdersNumber(AuthToken authToken);
}
