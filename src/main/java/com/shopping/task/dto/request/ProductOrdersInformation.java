package com.shopping.task.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrdersInformation {
    @NotNull
    private Integer productNumber;
    @NotNull
    private Integer productQuantity;
}
