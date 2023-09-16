package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.PaymentScheduleType;
import com.hlebon.produce8.service.request.MortgageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AcceleratedBiWeeklyStrategy implements PaymentScheduleStrategy {
    private static final int RESULT_SCALE = 2;
    private static final BigDecimal TWO = new BigDecimal(2);

    public final MonthlyPaymentCalculator monthlyPaymentCalculator;

    public AcceleratedBiWeeklyStrategy(MonthlyPaymentCalculator monthlyPaymentCalculator) {
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
    }

    @Override
    public PaymentScheduleType getPaymentScheduleType() {
        return PaymentScheduleType.ACCELERATED_BI_WEEKLY;
    }

    @Override
    public BigDecimal calculatePayment(MortgageRequest mortgageRequest) {
        BigDecimal monthlyPayment = monthlyPaymentCalculator.calculateMonthlyPayment(mortgageRequest);
        return monthlyPayment.divide(TWO, RESULT_SCALE, RESULT_ROUNDING_MODE);
    }
}
