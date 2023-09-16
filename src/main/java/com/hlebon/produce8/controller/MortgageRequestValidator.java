package com.hlebon.produce8.controller;

import com.hlebon.produce8.controller.dto.MortgageRequestDto;
import com.hlebon.produce8.service.PaymentScheduleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MortgageRequestValidator {

    private static final String PROPERTY_PRICE = "Property price";
    private static final String DOWN_PAYMENT = "Down payment";
    private static final String ANNUAL_INTEREST_RATE = "Annual interest rate";
    private static final String AMORTIZATION_PERIOD = "Amortization period";
    private static final String PAYMENT_SCHEDULE_TYPE = "Payment schedule type";
    private static final String MIN_PERCENTAGE_OF_DOWN_PAYMENT = "5";
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final int SCALE = 10;
    private static final BigDecimal MIN_DOWN_PAYMENT_DECIMAL = new BigDecimal(MIN_PERCENTAGE_OF_DOWN_PAYMENT).divide(ONE_HUNDRED, SCALE, RoundingMode.HALF_EVEN);

    public void validate(MortgageRequestDto mortgageRequest) {
        validatePropertyPrice(mortgageRequest.getPropertyPrice());
        validateDownPayment(mortgageRequest.getPropertyPrice(), mortgageRequest.getDownPayment());
        validateAnnualInterestRate(mortgageRequest.getAnnualInterestRate());
        validateAmortizationPeriod(mortgageRequest.getAmortizationPeriod());
        validatePaymentScheduleType(mortgageRequest.getPaymentScheduleType());
    }

    private void validatePaymentScheduleType(PaymentScheduleType paymentScheduleType) {
        if (paymentScheduleType == null) {
            throw mandatoryField(PAYMENT_SCHEDULE_TYPE);
        }
    }

    private void validateAmortizationPeriod(Integer amortizationPeriod) {
        if (amortizationPeriod == null) {
            throw mandatoryField(AMORTIZATION_PERIOD);
        }
        if (amortizationPeriod == 0) {
            throw zeroValueException(AMORTIZATION_PERIOD);
        }
        if (amortizationPeriod < 0) {
            throw negativeValueException(AMORTIZATION_PERIOD);
        }
        if (amortizationPeriod < 5 || amortizationPeriod > 30 || amortizationPeriod % 5 != 0) {
            throw new ValidationException(AMORTIZATION_PERIOD + " should be 5 year increments between 5 and 30 years");
        }
    }

    private void validateAnnualInterestRate(BigDecimal annualInterestRate) {
        if (isZero(annualInterestRate)) {
            throw zeroValueException(ANNUAL_INTEREST_RATE);
        }
        if (isNegative(annualInterestRate)) {
            throw negativeValueException(ANNUAL_INTEREST_RATE);
        }
    }

    private void validateDownPayment(BigDecimal propertyPrice, BigDecimal downPayment) {
        if (isNegative(downPayment)) {
            throw negativeValueException(DOWN_PAYMENT);
        }
        BigDecimal downPaymentPercentage = downPayment.divide(propertyPrice, SCALE, RoundingMode.HALF_EVEN);
        if (MIN_DOWN_PAYMENT_DECIMAL.compareTo(downPaymentPercentage) > 0) {
            throw new ValidationException("The minimum percentage of down payment is " + MIN_PERCENTAGE_OF_DOWN_PAYMENT + "%");
        }
    }

    private void validatePropertyPrice(BigDecimal propertyPrice) {
        if (isZero(propertyPrice)) {
            throw zeroValueException(PROPERTY_PRICE);
        }
        if (isNegative(propertyPrice)) {
            throw negativeValueException(PROPERTY_PRICE);
        }
    }

    private boolean isZero(BigDecimal number) {
        return BigDecimal.ZERO.equals(number);
    }

    private boolean isNegative(BigDecimal number) {
        return number.signum() == -1;
    }

    private ValidationException mandatoryField(String propertyName) {
        return generateException(propertyName, "is a mandatory field");
    }

    private ValidationException negativeValueException(String propertyName) {
        return generateException(propertyName, "can not be negative");
    }

    private ValidationException zeroValueException(String propertyName) {
        return generateException(propertyName, "can not be 0");
    }

    private ValidationException generateException(String propertyName, String message) {
        return new ValidationException(String.format("%s %s", propertyName, message));
    }
}
