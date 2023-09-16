package com.hlebon.produce8.controller.dto;

import lombok.Getter;

@Getter
public class MortgageResponseDto {
    private final String payment;

    public MortgageResponseDto(String payment) {
        this.payment = payment;
    }
}
