package com.shopping.task.service;

import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.request.GetParticularDateProductPriceRequestDto;
import com.shopping.task.dto.request.PatchAllProductRequstDto;
import com.shopping.task.dto.request.PatchPriceProductRequestDto;
import com.shopping.task.dto.request.PostProductRequestDto;
import com.shopping.task.dto.response.GetParticularDateProductPriceResponseDto;
import com.shopping.task.dto.response.GetProductListResponseDto;
import com.shopping.task.dto.response.GetProductPriceRecordResponseDto;
import com.shopping.task.dto.response.GetProductResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.provider.AuthToken;

public interface ProductService {
    public ResponseEntity<ResponseDto> postProduct(AuthToken authToken, PostProductRequestDto dto);
    public ResponseEntity<ResponseDto> patchAllProduct(AuthToken authToken, PatchAllProductRequstDto dto);
    public ResponseEntity<ResponseDto> patchPriceProduct(AuthToken authToken, PatchPriceProductRequestDto dto);
    public ResponseEntity<ResponseDto> deleteProduct(AuthToken authToken, Integer productNumber);
    public ResponseEntity<? super GetProductPriceRecordResponseDto> getProductPriceRecord(AuthToken authToken, Integer productNumber);
    public ResponseEntity<? super GetParticularDateProductPriceResponseDto> getParticularDateProductPrice(AuthToken authToken, GetParticularDateProductPriceRequestDto dto);
    public ResponseEntity<? super GetProductListResponseDto> getProductList(AuthToken authToken); 
    public ResponseEntity<? super GetProductResponseDto> getProduct(AuthToken authToken, Integer productNumber); 
}
