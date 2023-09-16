package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.request.MortgageRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyPaymentCalculatorTest {

    private final MonthlyPaymentCalculator calculator = new MonthlyPaymentCalculator();

    @Test
    void calculateMonthlyPayment() {
        // given
        MortgageRequest request = MortgageRequest.builder()
                .propertyPrice(new BigDecimal("350000"))
                .downPayment(new BigDecimal("50000"))
                .annualInterestRate(new BigDecimal("0.033"))
                .amortizationPeriod(25)
                .build();

        // expected
        BigDecimal expected = new BigDecimal("1469.88");

        // when
        BigDecimal result = calculator.calculateMonthlyPayment(request);

        // then
        assertEquals(expected, result.setScale(2, RoundingMode.HALF_EVEN));
    }
}
