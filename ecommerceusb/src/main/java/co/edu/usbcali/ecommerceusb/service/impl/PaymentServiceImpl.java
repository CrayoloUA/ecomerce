package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreatePaymentRequest;
import co.edu.usbcali.ecommerceusb.dto.PaymentResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdatePaymentRequest;
import co.edu.usbcali.ecommerceusb.mapper.PaymentMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Payment;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.PaymentRepository;
import co.edu.usbcali.ecommerceusb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return PaymentMapper.modelToPaymentResponse(payments);
    }

    @Override
    public PaymentResponse getPaymentById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Payment no encontrado con el id: %d", id)));
        return PaymentMapper.modelToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createPaymentRequest no puede ser nulo.");
        if (Objects.isNull(request.getOrderId()) || request.getOrderId() <= 0)
            throw new Exception("El campo orderId debe ser mayor a 0.");
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo.");
        if (Objects.isNull(request.getIdempotencyKey()) || request.getIdempotencyKey().isBlank())
            throw new Exception("El campo idempotencyKey no puede ser nulo.");
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        return PaymentMapper.modelToPaymentResponse(paymentRepository.save(PaymentMapper.createPaymentRequestToPayment(request, order)));
    }

    @Override
    public PaymentResponse updatePayment(Integer id, UpdatePaymentRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updatePaymentRequest no puede ser nulo.");
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Payment no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getStatus()) && !request.getStatus().isBlank())
            payment.setStatus(Payment.PaymentStatus.valueOf(request.getStatus()));
        if (!Objects.isNull(request.getProviderRef()) && !request.getProviderRef().isBlank())
            payment.setProviderRef(request.getProviderRef());
        return PaymentMapper.modelToPaymentResponse(paymentRepository.save(payment));
    }
}
