package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.request.MortgageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class MonthlyPaymentCalculator {

    private static final int NUMBER_MONTHS_IN_YEAR = 12;
    private static final BigDecimal NUMBER_MONTHS_IN_YEAR_DECIMAL = new BigDecimal(NUMBER_MONTHS_IN_YEAR);
    private static final BigDecimal ONE = new BigDecimal(1);

    private static final int INTERMEDIATE_SCALE = 10;

    public BigDecimal calculateMonthlyPayment(MortgageRequest mortgageRequest) {
        BigDecimal propertyPrice = mortgageRequest.getPropertyPrice();
        BigDecimal downPayment = mortgageRequest.getDownPayment();
        int amortizationPeriod = mortgageRequest.getAmortizationPeriod();
        BigDecimal annualInterestRate = mortgageRequest.getAnnualInterestRate();
        BigDecimal principle = propertyPrice.subtract(downPayment);
        BigDecimal monthlyInterestRate = annualInterestRate.divide(NUMBER_MONTHS_IN_YEAR_DECIMAL, INTERMEDIATE_SCALE, RoundingMode.HALF_EVEN); // FIXME check
        int totalNumberOfPayments = amortizationPeriod * 12;
        BigDecimal intermediate = monthlyInterestRate.add(ONE).pow(totalNumberOfPayments);

        return principle
                .multiply(monthlyInterestRate)
                .multiply(intermediate)
                .divide(intermediate.subtract(ONE), INTERMEDIATE_SCALE, RoundingMode.HALF_EVEN);
    }
}
