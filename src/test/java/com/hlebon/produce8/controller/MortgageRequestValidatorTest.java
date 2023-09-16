package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.service.PaymentScheduleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MortgageRequestValidatorTest {

    private final MortgageRequestValidator validator = new MortgageRequestValidator();

    @Test
    void validate_happyPath() {
        // given
        MortgageRequestDto request = defaultDto().build();

        // when
        validator.validate(request);
    }

    @Test
    void validate_propertyPriceZero() {
        // given
        MortgageRequestDto request = defaultDto()
                .propertyPrice(BigDecimal.ZERO)
                .build();

        // expected
        String expectedMessage = "Property price can not be 0";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_propertyPriceNegative() {
        // given
        MortgageRequestDto request = defaultDto()
                .propertyPrice(new BigDecimal("-1"))
                .build();

        // expected
        String expectedMessage = "Property price can not be negative";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_downPaymentNegative() {
        // given
        MortgageRequestDto request = defaultDto()
                .downPayment(new BigDecimal("-1"))
                .build();

        // expected
        String expectedMessage = "Down payment can not be negative";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_downPaymentOutOfRange() {
        // given
        MortgageRequestDto request = defaultDto()
                .propertyPrice(new BigDecimal("100"))
                .downPayment(new BigDecimal("4"))
                .build();

        // expected
        String expectedMessage = "The minimum percentage of down payment is 5%";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_annualInterestRateZero() {
        // given
        MortgageRequestDto request = defaultDto()
                .annualInterestRate(BigDecimal.ZERO)
                .build();

        // expected
        String expectedMessage = "Annual interest rate can not be 0";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_annualInterestRateNegative() {
        // given
        MortgageRequestDto request = defaultDto()
                .annualInterestRate(new BigDecimal("-1"))
                .build();

        // expected
        String expectedMessage = "Annual interest rate can not be negative";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_amortizationPeriodNull() {
        // given
        MortgageRequestDto request = defaultDto()
                .amortizationPeriod(null)
                .build();

        // expected
        String expectedMessage = "Amortization period is a mandatory field";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_amortizationPeriodZero() {
        // given
        MortgageRequestDto request = defaultDto()
                .amortizationPeriod(0)
                .build();

        // expected
        String expectedMessage = "Amortization period can not be 0";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_amortizationPeriodNegative() {
        // given
        MortgageRequestDto request = defaultDto()
                .amortizationPeriod(-1)
                .build();

        // expected
        String expectedMessage = "Amortization period can not be negative";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_amortizationPeriodLess5Years() {
        // given
        MortgageRequestDto request = defaultDto()
                .amortizationPeriod(3)
                .build();

        // expected
        String expectedMessage = "Amortization period should be 5 year increments between 5 and 30 years";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_amortizationPeriodMore30Years() {
        // given
        MortgageRequestDto request = defaultDto()
                .amortizationPeriod(31)
                .build();

        // expected
        String expectedMessage = "Amortization period should be 5 year increments between 5 and 30 years";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void validate_paymentScheduleTypeIsNull() {
        // given
        MortgageRequestDto request = defaultDto()
                .paymentScheduleType(null)
                .build();

        // expected
        String expectedMessage = "Payment schedule type is a mandatory field";

        // when
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validator.validate(request));

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    private MortgageRequestDto.MortgageRequestDtoBuilder defaultDto() {
        return MortgageRequestDto.builder()
                .propertyPrice(new BigDecimal("350000"))
                .downPayment(new BigDecimal("50000"))
                .annualInterestRate(new BigDecimal("3.3"))
                .amortizationPeriod(25)
                .paymentScheduleType(PaymentScheduleType.MONTHLY);
    }
}