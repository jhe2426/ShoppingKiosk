package com.shopping.task.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponApplicationOrdersInformation {
    @NotNull
    private Integer productNumber;
    @NotNull
    private Integer productQuantity;
}
