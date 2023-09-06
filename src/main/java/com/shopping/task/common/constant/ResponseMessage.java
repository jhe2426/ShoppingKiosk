package com.shopping.task.common.constant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shopping.task.dto.response.ResponseDto;

public interface ResponseMessage {
    public static final ResponseEntity<ResponseDto> SUCCESS 
    = ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("SU", "Success"));

    public static final ResponseEntity<ResponseDto> VAILDATION_FAILED
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("VF", "Request Parameter Validation Failed"));

    public static final ResponseEntity<ResponseDto> SAME_PRICE
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("SIP", "Same In Price"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_PRODUCT_PRICE_RECORD
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEPR", "Non-Existent Product Price Record"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_PRODCUT_MODIFY_PRICE
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEPMP", "Non-Existent Product Modify Price"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_USER_ID
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEUI", "Non-Existent User Id"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_PRODUCT_NUMBER
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEPN", "Non-Existent Product Number"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_COUPON_NUMBER
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NECN", "Non-Existent Coupon Number"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_USER_COUPON
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEUC", "Non-Existent User Coupon"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_APPLY_COUPON_PRODUCT_NUMBER
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEACPN", "No Exists Apply Coupon Product Number "));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_ORDERS_NUMBER
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEON","Non-Existent Orders Number"));

    public static final ResponseEntity<ResponseDto> NOT_EXIST_ORDER_HISTORY
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NEOH", "Non-Existent Order History"));

    public static final ResponseEntity<ResponseDto> NOT_CORRECT_DATE_FORMAT
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NCDF", "Not Correct Date Format"));

    public static final ResponseEntity<ResponseDto> NOT_CREATE_PRODUCT_DATE
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("NCRD", "Not Create Product Date"));

    public static final ResponseEntity<ResponseDto> DUPLICATED_PRODUCT_NUMBER_INPUT
    = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("DPNI", "Duplicated Product Number Input"));

    public static final ResponseEntity<ResponseDto> NO_PERMISSIONS
    = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto("NP", "No Permissions"));

    public static final ResponseEntity<ResponseDto> NOT_USER_TOKEN_ROLE
    = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto("NUTR", "Not User Token Role"));
    
    public static final ResponseEntity<ResponseDto> DATABASE_ERROR 
    = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DE", "Database Error"));
}
