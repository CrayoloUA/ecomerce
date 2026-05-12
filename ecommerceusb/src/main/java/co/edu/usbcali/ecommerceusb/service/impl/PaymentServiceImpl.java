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
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment no encontrado con el id: " + id));
        return PaymentMapper.modelToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) throws Exception {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        return PaymentMapper.modelToPaymentResponse(paymentRepository.save(PaymentMapper.createPaymentRequestToPayment(request, order)));
    }

    @Override
    public PaymentResponse updatePayment(Integer id, UpdatePaymentRequest request) throws Exception {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment no encontrado con el id: " + id));
        payment.setStatus(Payment.PaymentStatus.valueOf(request.getStatus()));
        payment.setProviderRef(request.getProviderRef());
        return PaymentMapper.modelToPaymentResponse(paymentRepository.save(payment));
    }
}
