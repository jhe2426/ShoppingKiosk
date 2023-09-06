package com.shopping.task.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetParticularDateProductPriceRequestDto {
    @NotBlank
    private String date;
    @NotNull
    private Integer productNumber;
}

//수정 하기
