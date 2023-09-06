package com.shopping.task.service.implement;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.task.common.constant.ResponseMessage;
import com.shopping.task.common.util.AuthTokenMartRoleValidation;
import com.shopping.task.dto.request.GetParticularDateProductPriceRequestDto;
import com.shopping.task.dto.request.PatchAllProductRequstDto;
import com.shopping.task.dto.request.PatchPriceProductRequestDto;
import com.shopping.task.dto.request.PostProductRequestDto;
import com.shopping.task.dto.response.GetParticularDateProductPriceResponseDto;
import com.shopping.task.dto.response.GetProductListResponseDto;
import com.shopping.task.dto.response.GetProductPriceRecordResponseDto;
import com.shopping.task.dto.response.GetProductResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.entity.CouponEntity;
import com.shopping.task.entity.ProductEntity;
import com.shopping.task.entity.ProductPriceRecordEntity;
import com.shopping.task.entity.UserCouponEntity;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.repository.CouponRepository;
import com.shopping.task.repository.ProductPriceRecordRepository;
import com.shopping.task.repository.ProductRepository;
import com.shopping.task.repository.UserCouponRepository;
import com.shopping.task.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImplement implements ProductService{
    
    private final ProductRepository productRepository;
    private final ProductPriceRecordRepository productPriceRecordRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> postProduct(AuthToken authToken, PostProductRequestDto dto) {

        boolean martRole = AuthTokenMartRoleValidation.martRoleValidation(authToken);

        if(!martRole) return ResponseMessage.NO_PERMISSIONS;

        ProductEntity productEntity = new ProductEntity(dto);

        try {

            productRepository.save(productEntity);

            ProductPriceRecordEntity priceRecordEntity = new ProductPriceRecordEntity(productEntity);
            productPriceRecordRepository.save(priceRecordEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseMessage.SUCCESS;  
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> patchAllProduct(AuthToken authToken, PatchAllProductRequstDto dto) {

        boolean martRole = AuthTokenMartRoleValidation.martRoleValidation(authToken);

        if(!martRole) return ResponseMessage.NO_PERMISSIONS;

        int productNumber = dto.getProductNumber();
        int inputProductPrice = dto.getPrice();

        try {

            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);

            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;
            
            int productPrice = productEntity.getPrice();

            boolean samePrice = (productPrice == inputProductPrice);

            if(!samePrice) {
                ProductPriceRecordEntity priceRecordEntity = new ProductPriceRecordEntity(productEntity);
                productPriceRecordRepository.save(priceRecordEntity);
            }

            ProductEntity updateProductEntity = new ProductEntity(productEntity, dto);
            productRepository.save(updateProductEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }
        
        return ResponseMessage.SUCCESS;
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> patchPriceProduct(AuthToken authToken, PatchPriceProductRequestDto dto) {

        boolean martRole = AuthTokenMartRoleValidation.martRoleValidation(authToken);

        if(!martRole) return ResponseMessage.NO_PERMISSIONS;

        int productNumber = dto.getProductNumber();
        int inputProductPrice = dto.getPrice();

        try {

            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);

            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;

            int productPrice = productEntity.getPrice();

            boolean samePrice = (productPrice == inputProductPrice);

            if(samePrice) return ResponseMessage.SAME_PRICE;

            ProductPriceRecordEntity priceRecordEntity = new ProductPriceRecordEntity(productEntity);
            productPriceRecordRepository.save(priceRecordEntity);

            ProductEntity updateProductEntity = new ProductEntity(productEntity, dto);
            productRepository.save(updateProductEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseMessage.SUCCESS;
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> deleteProduct(AuthToken authToken, Integer productNumber) {

        boolean martRole = AuthTokenMartRoleValidation.martRoleValidation(authToken);

        if (!martRole) return ResponseMessage.NO_PERMISSIONS;

        if (productNumber == null) return ResponseMessage.VAILDATION_FAILED;

        try {
            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);

            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;

            List<ProductPriceRecordEntity> productPriceRecordEntities = productPriceRecordRepository.findByProductEntity(productEntity);

            if (!(productPriceRecordEntities == null)) {
                productPriceRecordRepository.deleteAll(productPriceRecordEntities);
            }

            List<CouponEntity> couponEntities = couponRepository.findByCoverage(productNumber);

            if(!(couponEntities == null)) {

                for(CouponEntity couponEntity: couponEntities) {

                    List<UserCouponEntity> userCouponEntities = userCouponRepository.findByCouponEntity(couponEntity);
                    
                    userCouponRepository.deleteAll(userCouponEntities);
                }
                
                couponRepository.deleteAll(couponEntities);
            }

            productRepository.delete(productEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }
        
        return ResponseMessage.SUCCESS;
    }

    @Override
    public ResponseEntity<? super GetProductPriceRecordResponseDto> getProductPriceRecord(AuthToken authToken, Integer productNumber) {

        if(productNumber == null) return ResponseMessage.VAILDATION_FAILED;
        
        GetProductPriceRecordResponseDto body = null;

        try {
            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
            
            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;

            List<ProductPriceRecordEntity> productPriceRecordEntities = productPriceRecordRepository.findByProductEntityOrderByBeforeModifyPriceDesc(productEntity);

            if(productPriceRecordEntities.isEmpty()) return ResponseMessage.NOT_EXIST_PRODUCT_PRICE_RECORD;
            
            body = new GetProductPriceRecordResponseDto(productEntity, productPriceRecordEntities);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetParticularDateProductPriceResponseDto> getParticularDateProductPrice(AuthToken authToken, GetParticularDateProductPriceRequestDto dto) {
        GetParticularDateProductPriceResponseDto body = null;


        String date = dto.getDate();
        int productNumber = dto.getProductNumber();

        boolean correctDateFormat = checkInputDateFormat(date);

        if(!correctDateFormat) return ResponseMessage.NOT_CORRECT_DATE_FORMAT;


        try {

            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);

            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;

            List<ProductPriceRecordEntity> productPriceRecordEntities = productPriceRecordRepository.findByProductEntity(productEntity);

            for(ProductPriceRecordEntity productPriceRecordEntity: productPriceRecordEntities) {
                String modifyDate = productPriceRecordEntity.getModifyDate();
                
                if(modifyDate.equals(date)) {
                    int price = productPriceRecordEntity.getBeforeModifyPrice();
                    body = new GetParticularDateProductPriceResponseDto(price);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


    private boolean checkInputDateFormat(String inputDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(inputDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseEntity<? super GetProductListResponseDto> getProductList(AuthToken authToken) {

        GetProductListResponseDto body = null;

        try {

            List<ProductEntity> productEntities = productRepository.getProductList();
            body = new GetProductListResponseDto(productEntities);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<? super GetProductResponseDto> getProduct(AuthToken authToken, Integer productNumber) {

        if(productNumber == null) return ResponseMessage.VAILDATION_FAILED;

        GetProductResponseDto body = null;
        
        try {

            ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
            if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;

            body = new GetProductResponseDto(productEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
