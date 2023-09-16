package com.hlebon.produce8.service.response;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MortgageResponse {
    private final BigDecimal payment;

    public MortgageResponse(BigDecimal payment) {
        this.payment = payment;
    }
}
