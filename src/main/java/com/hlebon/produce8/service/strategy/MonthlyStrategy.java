package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.PaymentScheduleType;
import com.hlebon.produce8.service.request.MortgageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MonthlyStrategy implements PaymentScheduleStrategy {

    public final MonthlyPaymentCalculator monthlyPaymentCalculator;

    public MonthlyStrategy(MonthlyPaymentCalculator monthlyPaymentCalculator) {
        this.monthlyPaymentCalculator = monthlyPaymentCalculator;
    }

    @Override
    public PaymentScheduleType getPaymentScheduleType() {
        return PaymentScheduleType.MONTHLY;
    }

    @Override
    public BigDecimal calculatePayment(MortgageRequest mortgageRequest) {
        return monthlyPaymentCalculator.calculateMonthlyPayment(mortgageRequest).setScale(RESULT_SCALE, RESULT_ROUNDING_MODE);
    }
}
