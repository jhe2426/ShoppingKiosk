package com.shopping.task.dto.request;


import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAvailableCouponListRequestDto {
    
    @NotNull
    private List<Integer> productNumberList;
}
