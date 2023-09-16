package com.hlebon.produce8.mapper;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.controller.dto.MortgageResponseDto;
import com.hlebon.produce8.service.PaymentScheduleType;
import com.hlebon.produce8.service.request.MortgageRequest;
import com.hlebon.produce8.service.response.MortgageResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MortgageMapperTest {

    private final MortgageMapper mortgageMapper = new MortgageMapper();

    @Test
    void fromDto() {
        // given
        BigDecimal propertyPrice = new BigDecimal("350000");
        BigDecimal downPayment = new BigDecimal("50000");
        BigDecimal annualInterestRate = new BigDecimal("3.3");
        int amortizationPeriod = 25;
        PaymentScheduleType paymentScheduleType = PaymentScheduleType.MONTHLY;
        MortgageRequestDto dto = MortgageRequestDto.builder()
                .propertyPrice(propertyPrice)
                .downPayment(downPayment)
                .annualInterestRate(annualInterestRate)
                .amortizationPeriod(amortizationPeriod)
                .paymentScheduleType(paymentScheduleType)
                .build();

        // when
        MortgageRequest result = mortgageMapper.fromDto(dto);

        // then
        assertEquals(propertyPrice, result.getPropertyPrice());
        assertEquals(downPayment, result.getDownPayment());
        assertEquals(new BigDecimal("0.033").setScale(10, RoundingMode.HALF_EVEN), result.getAnnualInterestRate());
        assertEquals(amortizationPeriod, result.getAmortizationPeriod());
        assertEquals(paymentScheduleType, result.getPaymentScheduleType());
    }

    @Test
    void fromEntity() {
        // given
        String expected = "9823.23";
        MortgageResponse response = new MortgageResponse(new BigDecimal(expected));

        // when
        MortgageResponseDto result = mortgageMapper.toDto(response);

        // then
        assertEquals(expected, result.getPayment());
    }
}