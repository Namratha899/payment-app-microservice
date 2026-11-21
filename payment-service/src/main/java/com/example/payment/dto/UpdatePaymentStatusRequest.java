package com.example.payment.dto;

import com.example.payment.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePaymentStatusRequest(
        @NotNull(message = "status is required")
        PaymentStatus status
) {
}
