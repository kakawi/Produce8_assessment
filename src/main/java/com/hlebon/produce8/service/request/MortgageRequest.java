package com.hlebon.produce8.service.request;

import com.hlebon.produce8.service.PaymentScheduleType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class MortgageRequest {
    private BigDecimal propertyPrice;
    private BigDecimal downPayment;
    private BigDecimal annualInterestRate;
    private int amortizationPeriod;
    private PaymentScheduleType paymentScheduleType;
}
