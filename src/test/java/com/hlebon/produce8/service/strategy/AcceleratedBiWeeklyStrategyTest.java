package com.hlebon.produce8.service.strategy;

import com.hlebon.produce8.service.PaymentScheduleType;
import com.hlebon.produce8.service.request.MortgageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcceleratedBiWeeklyStrategyTest {

    @Mock
    private MonthlyPaymentCalculator monthlyPaymentCalculator;

    @InjectMocks
    private AcceleratedBiWeeklyStrategy strategy;

    @Test
    void getPaymentScheduleType() {
        // expected
        PaymentScheduleType expected = PaymentScheduleType.ACCELERATED_BI_WEEKLY;

        // then
        assertEquals(expected, strategy.getPaymentScheduleType());
    }

    @Test
    void calculatePayment() {
        // given
        MortgageRequest request = MortgageRequest.builder().build();
        BigDecimal monthlyPayment = new BigDecimal("14.00005679");
        when(monthlyPaymentCalculator.calculateMonthlyPayment(request)).thenReturn(monthlyPayment);

        // expected
        BigDecimal expected = new BigDecimal("7.00");

        // when
        BigDecimal result = strategy.calculatePayment(request);

        // then
        assertEquals(expected, result);
    }
}
