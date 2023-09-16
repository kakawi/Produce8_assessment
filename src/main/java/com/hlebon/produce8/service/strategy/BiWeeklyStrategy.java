package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.PaymentScheduleType;
import com.hlebon.produce8.service.request.MortgageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BiWeeklyStrategy implements PaymentScheduleStrategy {
    private static final int RESULT_SCALE = 2;
    private static final BigDecimal NUMBER_OF_MONTHS_IN_YEAR = new BigDecimal(12);
    private static final BigDecimal NUMBER_OF_PAYMENTS_IN_YEAR = new BigDecimal(26);

    public final MonthlyPaymentCalculator monthlyPaymentCalculator;

    public BiWeeklyStrategy(MonthlyPaymentCalculator monthlyPaymentCalculator) {
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
    }

    @Override
    public PaymentScheduleType getPaymentScheduleType() {
        return PaymentScheduleType.BI_WEEKLY;
    }

    @Override
    public BigDecimal calculatePayment(MortgageRequest mortgageRequest) {
        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPayment(mortgageRequest);
        return monthlyPayment.multiply(NUMBER_OF_MONTHS_IN_YEAR).divide(NUMBER_OF_PAYMENTS_IN_YEAR, RESULT_SCALE, RESULT_ROUNDING_MODE);
    }
}
