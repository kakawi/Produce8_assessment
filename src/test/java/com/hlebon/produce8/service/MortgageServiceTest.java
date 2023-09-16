package com.hlebon.produce8.service;

import com.hlebon.produce8.service.request.MortgageRequest;
import com.hlebon.produce8.service.response.MortgageResponse;
import com.hlebon.produce8.service.strategy.PaymentScheduleStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MortgageServiceTest {

    @Test
    void calculateMortgagePayment_happyPath() {
        // given
        PaymentScheduleStrategy strategy = mock(PaymentScheduleStrategy.class);
        when(strategy.getPaymentScheduleType()).thenReturn(PaymentScheduleType.MONTHLY);
        BigDecimal expected = new BigDecimal(10);
        when(strategy.calculatePayment(any())).thenReturn(expected);
        List<PaymentScheduleStrategy> strategies = List.of(strategy);
        MortgageService service = new MortgageService(strategies);

        MortgageRequest request = MortgageRequest.builder().paymentScheduleType(PaymentScheduleType.MONTHLY).build();

        // when
        MortgageResponse result = service.calculateMortgagePayment(request);

        // then
        assertEquals(expected, result.getPayment());
    }

    @Test
    void calculateMortgagePayment_unknownStrategy() {
        // given
        List<PaymentScheduleStrategy> strategies = List.of();
        MortgageService service = new MortgageService(strategies);

        MortgageRequest request = MortgageRequest.builder().paymentScheduleType(PaymentScheduleType.MONTHLY).build();
        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.calculateMortgagePayment(request);
        });

        // then
        assertEquals("There is no Payment Strategy for MONTHLY", exception.getMessage());
    }
}
