package com.shopping.task.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetParticularDateProductPriceRequestDto {
    @NotBlank
    private String date;
    @NotNull
    private Integer productNumber;
}

