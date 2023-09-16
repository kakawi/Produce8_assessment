package com.hlebon.produce8.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlebon.produce8.service.PaymentScheduleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MortgageRequestDto {

    @JsonProperty
    @Schema(title = "Property Price - Dollars", example = "350000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal propertyPrice;

    @JsonProperty
    @Schema(title = "Down Payment - Dollars", example = "50000", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal downPayment;

    @JsonProperty
    @Schema(title = "Annual Interest Rate - Percentage", example = "3.3", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal annualInterestRate;

    @JsonProperty
    @Schema(title = "Amortization Period - Years", example = "25", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer amortizationPeriod;

    @JsonProperty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private PaymentScheduleType paymentScheduleType;
}
