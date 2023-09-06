package com.shopping.task.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.task.common.constant.CouponType;
import com.shopping.task.common.constant.ResponseMessage;
import com.shopping.task.dto.request.CouponApplicationOrdersInformation;
import com.shopping.task.dto.request.GetAvailableCouponListRequestDto;
import com.shopping.task.dto.request.PostCouponApplicationOrdersRequestDto;
import com.shopping.task.dto.request.PostProductOrdersRequestDto;
import com.shopping.task.dto.request.ProductOrdersInformation;
import com.shopping.task.dto.response.GetAvailableCouponListResponseDto;
import com.shopping.task.dto.response.GetOrdersResponseDto;
import com.shopping.task.dto.response.GetRecentOrdersNumberResponseDto;
import com.shopping.task.dto.response.ResponseDto;
import com.shopping.task.entity.CouponEntity;
import com.shopping.task.entity.OrdersEntity;
import com.shopping.task.entity.OrdersProductEntity;
import com.shopping.task.entity.ProductEntity;
import com.shopping.task.entity.UserCouponEntity;
import com.shopping.task.entity.UserEntity;
import com.shopping.task.provider.AuthToken;
import com.shopping.task.repository.CouponRepository;
import com.shopping.task.repository.OrdersProductRepository;
import com.shopping.task.repository.OrdersRepository;
import com.shopping.task.repository.ProductRepository;
import com.shopping.task.repository.UserCouponRepository;
import com.shopping.task.repository.UserRepository;
import com.shopping.task.service.OrdersService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersServiceImplement implements OrdersService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> postProductOrders(AuthToken authToken, PostProductOrdersRequestDto dto) {

        String role = authToken.getRole();
        String userId = authToken.getId();
        
        if(role.equals("mart")) return ResponseMessage.NOT_USER_TOKEN_ROLE;

        List<ProductOrdersInformation> productOrdersInpormationList = dto.getProductOrdersInformationList();
        
        try {

            UserEntity userEntity = userRepository.findById(userId);
            if(userEntity == null) return ResponseMessage.NOT_EXIST_USER_ID;


            for(int index = 0; index < productOrdersInpormationList.size(); index++) {
                ProductOrdersInformation productOrdersInpormation = productOrdersInpormationList.get(index);
                int productNumber = productOrdersInpormation.getProductNumber();

                for(int subIndex = index + 1; subIndex < productOrdersInpormationList.size(); subIndex++) {
                    ProductOrdersInformation subProductOrdersInpormation = productOrdersInpormationList.get(subIndex);
                    boolean isEqualsProductNumber = (productNumber == subProductOrdersInpormation.getProductNumber());
                    if(isEqualsProductNumber) return ResponseMessage.DUPLICATED_PRODUCT_NUMBER_INPUT;
                }
            }

            for(ProductOrdersInformation productOrdersInformation: productOrdersInpormationList) {
                int productNumber = productOrdersInformation.getProductNumber();
                ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
                if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;
            }           

            List<ProductEntity> productEntities = new ArrayList<>();

            for(ProductOrdersInformation productOrdersInpormation: productOrdersInpormationList) {
                int productNumber = productOrdersInpormation.getProductNumber();
                ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
                productEntities.add(productEntity);
            }

            int totalProductCount = 0;
            int totalProdcutPrice = 0;
            
            for(int index = 0; index < productEntities.size(); index++) {
                int productNumber = productEntities.get(index).getProductNumber();
                int productPrice = productEntities.get(index).getPrice();
                int inputProductNumber = productOrdersInpormationList.get(index).getProductNumber();

                if(productNumber == inputProductNumber) {
                    int inputProductQuantity = productOrdersInpormationList.get(index).getProductQuantity();
                    totalProdcutPrice += productPrice * inputProductQuantity;
                    totalProductCount += inputProductQuantity;
                }
            }

            int deliveryChargeResult = 0;

            for(ProductEntity productEntity: productEntities) {
                int deliveryCharge = productEntity.getDeliveryCharge();

                if(!(deliveryCharge == 0)) {
                    deliveryChargeResult = deliveryCharge;
                    break;
                }
            }

            totalProdcutPrice += deliveryChargeResult;

            OrdersEntity ordersEntity = new OrdersEntity(userEntity, totalProductCount, totalProdcutPrice, deliveryChargeResult);
            ordersRepository.save(ordersEntity);


            for(int index = 0; index < productEntities.size(); index++) {
                int productNumber = productEntities.get(index).getProductNumber();
                int inputProductNumber = productOrdersInpormationList.get(index).getProductNumber();
                ProductEntity productEntity = productEntities.get(index);

                if(productNumber == inputProductNumber) {
                    int inputProductQuantity = productOrdersInpormationList.get(index).getProductQuantity();
                    OrdersProductEntity ordersProductEntity = new OrdersProductEntity(ordersEntity, productEntity, inputProductQuantity);
                    ordersProductRepository.save(ordersProductEntity);
                }
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }
        return ResponseMessage.SUCCESS;
    }


    @Transactional
    @Override
    public ResponseEntity<ResponseDto> postCouponApplicationOrders(AuthToken authToken, PostCouponApplicationOrdersRequestDto dto) {



        String role = authToken.getRole();
        String userId = authToken.getId();

        if(role.equals("mart")) return ResponseMessage.NOT_USER_TOKEN_ROLE;

        List<CouponApplicationOrdersInformation> ordersRequestDtoList = dto.getProductOrdersInformationList();
        int couponNumber = dto.getCouponNumber();

        try {

            UserEntity userEntity = userRepository.findById(userId);
            if(userEntity == null) return ResponseMessage.NOT_EXIST_USER_ID;

             for(int index = 0; index < ordersRequestDtoList.size(); index++) {
                CouponApplicationOrdersInformation ordersRequestDto = ordersRequestDtoList.get(index);
                int productNumber = ordersRequestDto.getProductNumber();

                for(int subIndex = index + 1; subIndex < ordersRequestDtoList.size(); subIndex++) {
                    CouponApplicationOrdersInformation subOrdersRequestDto = ordersRequestDtoList.get(subIndex);
                    boolean isEqualsProductNumber = (productNumber == subOrdersRequestDto.getProductNumber());
                    if(isEqualsProductNumber) return ResponseMessage.DUPLICATED_PRODUCT_NUMBER_INPUT;
                }
            }

            for(CouponApplicationOrdersInformation ordersRequstDto: ordersRequestDtoList) {
                int productNumber = ordersRequstDto.getProductNumber();
                ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
                if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;
            }


            CouponEntity couponEntity = couponRepository.findByCouponNumber(couponNumber);
            if(couponEntity == null) return ResponseMessage.NOT_EXIST_COUPON_NUMBER;

            UserCouponEntity userCouponEntity = userCouponRepository.findByUserEntityAndCouponEntity(userEntity, couponEntity);
            if(userCouponEntity == null) return ResponseMessage.NOT_EXIST_USER_COUPON;
            

            CouponType couponType = couponEntity.getCouponType();
            int coverage = couponEntity.getCoverage();
            int couponApplicationPrice = couponEntity.getApplicationPrice();

            List<ProductEntity> productEntities = new ArrayList<>();

            for(CouponApplicationOrdersInformation ordersRequestDto: ordersRequestDtoList) {
                int productNumber = ordersRequestDto.getProductNumber();
                ProductEntity productEntity = productRepository.findByProductNumber(productNumber);
                productEntities.add(productEntity);
            }

            int totalProductCount = 0;
            int totalProdcutPrice = 0;
            boolean hasCoverage = !(coverage == 0);
        
            if(!hasCoverage) {
                
                if(couponType.toString().equals("FIXATION")) {
                    for(int index = 0; index < productEntities.size(); index++) {
                        int productNumber = productEntities.get(index).getProductNumber();
                        int productPrice = productEntities.get(index).getPrice();
                        int inputProductNumber = ordersRequestDtoList.get(index).getProductNumber();

                        if(productNumber == inputProductNumber) {
                            int inputProductQuantity = ordersRequestDtoList.get(index).getProductQuantity();
                            totalProdcutPrice += productPrice * inputProductQuantity;
                            totalProductCount += inputProductQuantity;
                        }
                    }
                    totalProdcutPrice -= couponApplicationPrice;
                }

                if(couponType.toString().equals("RATIO")) {

                    double percentage = couponApplicationPrice * 0.01;

                    for(int index = 0; index < productEntities.size(); index++) {
                        int productNumber = productEntities.get(index).getProductNumber();
                        int productPrice = productEntities.get(index).getPrice();
                        int inputProductNumber = ordersRequestDtoList.get(index).getProductNumber();

                        if(productNumber == inputProductNumber) {
                            int inputProductQuantity = ordersRequestDtoList.get(index).getProductQuantity();
                            totalProdcutPrice += productPrice * inputProductQuantity;
                            totalProductCount += inputProductQuantity;
                        }
                    }         

                    int applicationPercentagePrice = (int)(totalProdcutPrice * percentage);
                    totalProdcutPrice -= applicationPercentagePrice;
                }
            }


            if(hasCoverage) {

                boolean existSameProductNumber = false;
                for(CouponApplicationOrdersInformation ordersRequestDto: ordersRequestDtoList) {
                    int ordersProductNumber = ordersRequestDto.getProductNumber();
                        
                    if(coverage == ordersProductNumber) {
                        existSameProductNumber = true;
                        break;
                    }
                }

                if(!existSameProductNumber) return ResponseMessage.NOT_EXIST_APPLY_COUPON_PRODUCT_NUMBER;

                if(couponType.toString().equals("FIXATION")) {

                    for(int index = 0; index < productEntities.size(); index++) {
                        int productNumber = productEntities.get(index).getProductNumber();
                        int productPrice = productEntities.get(index).getPrice();
                        int inputProductNumber = ordersRequestDtoList.get(index).getProductNumber();

                        if(productNumber == inputProductNumber) {
                            int inputProductQuantity = ordersRequestDtoList.get(index).getProductQuantity();

                            if(productNumber == coverage) {
                                totalProdcutPrice += productPrice * inputProductQuantity;
                                totalProdcutPrice -= couponApplicationPrice;
                                totalProductCount += inputProductQuantity;
                                continue;
                            }     

                            totalProdcutPrice += productPrice * inputProductQuantity;
                            totalProductCount += inputProductQuantity;
            
                        }
                    }

                }

                if(couponType.toString().equals("RATIO")) {

                    double percentage = couponApplicationPrice * 0.01;


                    for(int index = 0; index < productEntities.size(); index++) {
                        int productNumber = productEntities.get(index).getProductNumber();
                        int productPrice = productEntities.get(index).getPrice();
                        int inputProductNumber = ordersRequestDtoList.get(index).getProductNumber();

                        if(productNumber == inputProductNumber) {
                            int inputProductQuantity = ordersRequestDtoList.get(index).getProductQuantity();

                            if(productNumber == coverage) {
                                totalProdcutPrice += productPrice * inputProductQuantity;
                                totalProductCount += inputProductQuantity;

                                int applicationPercentagePrice = (int)(totalProdcutPrice * percentage);
                                totalProdcutPrice -= applicationPercentagePrice;
                                continue;
                            }     

                            totalProdcutPrice += productPrice * inputProductQuantity;
                            totalProductCount += inputProductQuantity;
            
                        }
                    }
                    
                }

            }

            
            int deliveryChargeResult = 0;

            for(ProductEntity productEntity: productEntities) {
                int deliveryCharge = productEntity.getDeliveryCharge();

                if(!(deliveryCharge == 0)) {
                    deliveryChargeResult = deliveryCharge;
                    break;
                }
            }

            totalProdcutPrice += deliveryChargeResult;

            OrdersEntity ordersEntity = new OrdersEntity(userEntity, totalProductCount, totalProdcutPrice, couponNumber, deliveryChargeResult);
            ordersRepository.save(ordersEntity);
            
            for(int index = 0; index < productEntities.size(); index++) {
                int productNumber = productEntities.get(index).getProductNumber();
                int inputProductNumber = ordersRequestDtoList.get(index).getProductNumber();
                ProductEntity productEntity = productEntities.get(index);

                if(productNumber == inputProductNumber) {
                    int inputProductQuantity = ordersRequestDtoList.get(index).getProductQuantity();
                    OrdersProductEntity ordersProductEntity = new OrdersProductEntity(ordersEntity, productEntity, inputProductQuantity);
                    ordersProductRepository.save(ordersProductEntity);
                }
            }            

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }


        return ResponseMessage.SUCCESS;
    }


    @Override
    public ResponseEntity<? super GetAvailableCouponListResponseDto> getAvailableCouponList(AuthToken authToken,GetAvailableCouponListRequestDto dto) {
        
        String role = authToken.getRole();
        String userId = authToken.getId();

        if(role.equals("mart")) return ResponseMessage.NOT_USER_TOKEN_ROLE;

        GetAvailableCouponListResponseDto body = null;
        List<CouponEntity> couponEntities = new ArrayList<>();

        List<Integer> inputProductNumberList = dto.getProductNumberList();

        try {

            UserEntity userEntity = userRepository.findById(userId);
            if(userEntity == null) return ResponseMessage.NOT_EXIST_USER_ID;

            for(int index = 0; index < inputProductNumberList.size(); index++) {

                int inputProductNumber = inputProductNumberList.get(index);

                for(int subIndex = index +1; subIndex < inputProductNumberList.size(); subIndex++) {
                    int subInputProductNumber = inputProductNumberList.get(subIndex);
                    boolean isEqualsProductNumber = (inputProductNumber == subInputProductNumber);
                    if(isEqualsProductNumber) return ResponseMessage.DUPLICATED_PRODUCT_NUMBER_INPUT;
                }
            }

            for(Integer inputProductNumber: inputProductNumberList) {
                ProductEntity productEntity = productRepository.findByProductNumber(inputProductNumber);
                if(productEntity == null) return ResponseMessage.NOT_EXIST_PRODUCT_NUMBER;
            }

            List<UserCouponEntity> userCouponEntities = userCouponRepository.findByUserEntity(userEntity);

            if(userCouponEntities.isEmpty()) return ResponseMessage.NOT_EXIST_USER_COUPON;

      

            for(int index = 0; index < userCouponEntities.size(); index++) {
                
                UserCouponEntity userCouponEntity = userCouponEntities.get(index);
                CouponEntity couponEntity = userCouponEntity.getCouponEntity();

                int couponCoverage = couponEntity.getCoverage();

                if(couponCoverage == 0) {
                    couponEntities.add(couponEntity);
                }

                for(Integer inputProductNumber: inputProductNumberList) {
                    if(inputProductNumber == couponCoverage) {
                        couponEntities.add(couponEntity);
                    }
                }
            }

            Collections.sort(couponEntities);

            body = new GetAvailableCouponListResponseDto(couponEntities);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);

    }


    @Override
    public ResponseEntity<? super GetOrdersResponseDto> getOrders(AuthToken authToken, Integer ordersNumber) {
        String role = authToken.getRole();
        String userId = authToken.getId();

        if(role.equals("mart")) return ResponseMessage.NOT_USER_TOKEN_ROLE;

        if(ordersNumber == null) return ResponseMessage.VAILDATION_FAILED;

        GetOrdersResponseDto body = null;
        

        try {

            UserEntity userEntity = userRepository.findById(userId);
            if(userEntity == null) return ResponseMessage.NOT_EXIST_USER_ID;

            OrdersEntity ordersEntity = ordersRepository.findByOrdersNumber(ordersNumber);
            
            if(ordersEntity == null) return ResponseMessage.NOT_EXIST_ORDERS_NUMBER;

            UserEntity ordersEntityUserEntity = ordersEntity.getUserEntity();
            String ordersEntityUserId = ordersEntityUserEntity.getId();

            if(!userId.equals(ordersEntityUserId)) return ResponseMessage.NO_PERMISSIONS;

            List<OrdersProductEntity> ordersProductEntities = ordersProductRepository.findByOrdersEntity(ordersEntity);

            body = new GetOrdersResponseDto(ordersEntity, ordersProductEntities);            

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }


    @Override
    public ResponseEntity<? super GetRecentOrdersNumberResponseDto> getRecentOrdersNumber(AuthToken authToken) {

        String role = authToken.getRole();
        String userId = authToken.getId();

        if(role.equals("mart")) return ResponseMessage.NOT_USER_TOKEN_ROLE;

        GetRecentOrdersNumberResponseDto body = null;


        try {

            UserEntity userEntity = userRepository.findById(userId);
            if(userEntity == null) return ResponseMessage.NOT_EXIST_USER_ID;

            List<OrdersEntity> ordersEntities = ordersRepository.findByUserEntity(userEntity);

            if(ordersEntities.isEmpty()) return ResponseMessage.NOT_EXIST_ORDER_HISTORY;

            int ordersNumber = ordersRepository.getRecentOrdersNumber(userEntity);

            body = new GetRecentOrdersNumberResponseDto(ordersNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseMessage.DATABASE_ERROR;
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
        
    }
}
