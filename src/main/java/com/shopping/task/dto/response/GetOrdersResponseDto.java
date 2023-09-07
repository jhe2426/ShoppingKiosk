package com.shopping.task.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.shopping.task.entity.OrdersEntity;
import com.shopping.task.entity.OrdersProductEntity;
import com.shopping.task.entity.ProductEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetOrdersResponseDto extends ResponseDto{
    private int ordersNumber;
    private int userNumber;
    private int totalOrderCount;
    private int orderPrice;
    private int couponPkNumber;
    private int deliveryCharge;
    private List<OrdersProduct> ordersProdcutList;

    public GetOrdersResponseDto(OrdersEntity ordersEntity, List<OrdersProductEntity> ordersProductEntities) {

        super("SU", "Success");

        List<OrdersProduct> ordersProdcutList = new ArrayList<>();
        
        for(OrdersProductEntity ordersProductEntity: ordersProductEntities) {
            OrdersProduct ordersProduct = new OrdersProduct(ordersProductEntity);
            ordersProdcutList.add(ordersProduct);
        }

        this.ordersNumber = ordersEntity.getOrdersNumber();
        this.userNumber = ordersEntity.getUserEntity().getUserNumber();
        this.totalOrderCount = ordersEntity.getTotalOrderCount();
        this.orderPrice = ordersEntity.getOrderPrice();
        this.couponPkNumber = ordersEntity.getCouponPkNumber();
        this.deliveryCharge = ordersEntity.getDeliveryCharge();
        this.ordersProdcutList = ordersProdcutList;
    }

}

@Data
@NoArgsConstructor
class OrdersProduct {
    private int ordersProductNumber;
    private int productNumber;
    private int productQuantity;

    public OrdersProduct(OrdersProductEntity ordersProductEntity) {
        int productNumber = ordersProductEntity.getProductNumber();
        this.ordersProductNumber = ordersProductEntity.getOrdersProductNumber();
        this.productNumber =productNumber;
        this.productQuantity = ordersProductEntity.getProductQuantity();
    }
}




