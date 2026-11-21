package com.example.payment.service;

import com.example.payment.dto.CreatePaymentRequest;
import com.example.payment.exception.PaymentNotFoundException;
import com.example.payment.model.Payment;
import com.example.payment.model.PaymentStatus;
import com.example.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = new Payment(
                request.customerId(),
                request.amount(),
                request.currency().toUpperCase()
        );

        // Demo only: simulate a successful payment authorization.
        // A real payment integration must call a payment provider securely.
        payment.updateStatus(PaymentStatus.SUCCESS);

        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public Payment getPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }

    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional
    public Payment updatePaymentStatus(UUID paymentId, PaymentStatus status) {
        Payment payment = getPayment(paymentId);

        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new IllegalArgumentException("A refunded payment cannot be updated");
        }

        payment.updateStatus(status);
        return paymentRepository.save(payment);
    }
}
