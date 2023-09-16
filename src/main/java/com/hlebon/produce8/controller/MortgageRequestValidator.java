package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MortgageRequestValidator {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public void validate(MortgageRequestDto mortgageRequest) {
        if (zeroOrNegative(mortgageRequest.getPropertyPrice())) {
            throw new ValidationException("Property price can not be 0 or negative");
        }
    }

    private boolean zeroOrNegative(BigDecimal number) {
        return number.signum() == -1 || number.signum() == 0;
    }
}
