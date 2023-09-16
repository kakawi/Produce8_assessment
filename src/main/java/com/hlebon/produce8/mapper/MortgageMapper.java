package com.hlebon.produce8.mapper;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.controller.dto.MortgageResponseDto;
import com.hlebon.produce8.service.request.MortgageRequest;
import com.hlebon.produce8.service.response.MortgageResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MortgageMapper {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final int INTERMEDIATE_SCALE = 10;

    public MortgageRequest fromDto(MortgageRequestDto dto) {
        return MortgageRequest.builder()
                .propertyPrice(dto.getPropertyPrice())
                .downPayment(dto.getDownPayment())
                .annualInterestRate(dto.getAnnualInterestRate().divide(ONE_HUNDRED, INTERMEDIATE_SCALE, RoundingMode.HALF_EVEN))
                .amortizationPeriod(dto.getAmortizationPeriod())
                .paymentScheduleType(dto.getPaymentScheduleType())
                .build();
    }

    public MortgageResponseDto toDto(MortgageResponse response) {
        return new MortgageResponseDto(response.getPayment().toString());
    }
}
