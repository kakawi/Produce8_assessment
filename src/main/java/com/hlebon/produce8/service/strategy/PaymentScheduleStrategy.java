package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.request.MortgageRequest;
import com.hlebon.produce8.service.PaymentScheduleType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface PaymentScheduleStrategy {

    int RESULT_SCALE = 2;
    RoundingMode RESULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    PaymentScheduleType getPaymentScheduleType();
    BigDecimal calculatePayment(MortgageRequest mortgageRequest);
}
