package com.shopping.task.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.task.common.constant.RequestPattern;
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
import com.shopping.task.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(RequestPattern.PRODUCT_API)
public class ProductController {
    private final ProductService productService;

    private final String POST_PRODUCT = "";
    private final String PATCH_ALL_PRODUCT = "";
    private final String PATCH_PRICE_PRODUCT = "price";
    private final String DELETE_PRODUCT = "/{productNumber}";
    private final String GET_PRODUCT_PRICE_RECORD = "price-record/{productNumber}";
    private final String GET_PARTICULAR_DATE_PRODUCT_PRICE = "price";
    private final String GET_PRODUCT_LIST ="list";
    private final String GET_PRODUCT ="/{productNumber}";

    @PostMapping(POST_PRODUCT)
    public ResponseEntity<ResponseDto> postProduct(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody PostProductRequestDto reqeust
    ) {
        ResponseEntity<ResponseDto> response = productService.postProduct(authToken, reqeust);
        return response;
    }

    @PatchMapping(PATCH_ALL_PRODUCT)
    public ResponseEntity<ResponseDto> patchAllProduct(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody PatchAllProductRequstDto request
    ) {
        ResponseEntity<ResponseDto> response = productService.patchAllProduct(authToken, request);
        return response;
    }

    @PatchMapping(PATCH_PRICE_PRODUCT)
    public ResponseEntity<ResponseDto> patchPriceProduct(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody PatchPriceProductRequestDto request
    ) {
        ResponseEntity<ResponseDto> response = productService.patchPriceProduct(authToken, request);
        return response;
    }

    @DeleteMapping(DELETE_PRODUCT)
    public ResponseEntity<ResponseDto> deleteProduct(
        @AuthenticationPrincipal AuthToken authToken,
        @PathVariable("productNumber") Integer productNumber
    ) {
        ResponseEntity<ResponseDto> resposne = productService.deleteProduct(authToken, productNumber);
        return resposne;
    }

    @GetMapping(GET_PRODUCT_PRICE_RECORD)
    public ResponseEntity<? super GetProductPriceRecordResponseDto> getProductPriceRecord(
        @AuthenticationPrincipal AuthToken authToken,
        @PathVariable("productNumber") Integer prodcutNumber
    ) {
        ResponseEntity<? super GetProductPriceRecordResponseDto> response = productService.getProductPriceRecord(authToken, prodcutNumber);
        return response;
    }

    @GetMapping(GET_PARTICULAR_DATE_PRODUCT_PRICE)
    public ResponseEntity<? super GetParticularDateProductPriceResponseDto> getParticularDateProductPrice(
        @AuthenticationPrincipal AuthToken authToken,
        @Valid @RequestBody GetParticularDateProductPriceRequestDto request
    ) {
        ResponseEntity<? super GetParticularDateProductPriceResponseDto> response = productService.getParticularDateProductPrice(authToken, request);
        return response;
    }   

    @GetMapping(GET_PRODUCT_LIST)
    public ResponseEntity<? super GetProductListResponseDto> getProductList(
        @AuthenticationPrincipal AuthToken authToken
    ) {
        ResponseEntity<? super GetProductListResponseDto> response = productService.getProductList(authToken);
        return response;
    }

    @GetMapping(GET_PRODUCT)
    public ResponseEntity<? super GetProductResponseDto> getProduct(
        @AuthenticationPrincipal AuthToken authToken,
        @PathVariable("productNumber") Integer productNumber
    ) {
        ResponseEntity<? super GetProductResponseDto> response = productService.getProduct(authToken, productNumber);
        return response;
    }
}
