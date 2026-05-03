package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.PaymentRepository;
import co.edu.usbcali.ecommerceusb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<PaymentResponse> getPayments() {
        List<Payment> payments = paymentRepository.findAll();
        if (payments.isEmpty()) return List.of();
        return payments.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getPaymentById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment no encontrado con id: " + id));
        return toResponse(payment);
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getOrderId()) || request.getOrderId() <= 0)
            throw new Exception("El campo orderId debe ser mayor a 0");
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo");
        if (Objects.isNull(request.getIdempotencyKey()) || request.getIdempotencyKey().isBlank())
            throw new Exception("El campo idempotencyKey no puede ser nulo");

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));

        Payment payment = Payment.builder()
                .order(order)
                .status(Payment.PaymentStatus.valueOf(request.getStatus()))
                .providerRef(request.getProviderRef())
                .idempotencyKey(request.getIdempotencyKey())
                .createdAt(OffsetDateTime.now())
                .build();

        return toResponse(paymentRepository.save(payment));
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .orderId(p.getOrder().getId())
                .status(p.getStatus().name())
                .providerRef(p.getProviderRef())
                .idempotencyKey(p.getIdempotencyKey())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
