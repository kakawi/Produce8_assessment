package com.hlebon.produce8.service;

import com.hlebon.produce8.service.request.MortgageRequest;
import com.hlebon.produce8.service.response.MortgageResponse;
import com.hlebon.produce8.service.strategy.PaymentScheduleStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MortgageService {

    private final Map<PaymentScheduleType, PaymentScheduleStrategy> paymentStrategies = new HashMap<>();

    public MortgageService(List<PaymentScheduleStrategy> strategies) {
        for (PaymentScheduleStrategy strategy : strategies) {
            paymentStrategies.put(strategy.getPaymentScheduleType(), strategy);
        }
    }

    public MortgageResponse calculateMortgagePayment(MortgageRequest mortgageRequest) {
        PaymentScheduleStrategy strategy = paymentStrategies.get(mortgageRequest.getPaymentScheduleType());
        if (strategy == null) {
            throw new IllegalArgumentException("There is no Payment Strategy for " + mortgageRequest.getPaymentScheduleType().name());
        }
        BigDecimal payment = strategy.calculatePayment(mortgageRequest);
        return new MortgageResponse(payment);
    }
}
