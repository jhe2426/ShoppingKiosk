package com.shopping.task.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRecentOrdersNumberResponseDto extends ResponseDto{
    private int recentOrdersNumber;

    public GetRecentOrdersNumberResponseDto(int ordersNumber) {
        super("SU", "Success");
        this.recentOrdersNumber = ordersNumber;
    }
}
