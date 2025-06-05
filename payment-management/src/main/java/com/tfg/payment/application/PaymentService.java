package com.tfg.payment.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;

import com.stripe.param.PaymentIntentUpdateParams;
import com.tfg.payment.infrastructure.inbound.rest.dto.request.CartProductRequest;
import com.tfg.payment.infrastructure.inbound.rest.dto.request.CartRequest;
import com.tfg.payment.infrastructure.inbound.rest.dto.response.DeliveryMethodResponse;
import com.tfg.payment.infrastructure.outbound.persistence.delivery_method.PostgreSQLDeliveryMethodRepository;
import com.tfg.payment.infrastructure.outbound.persistence.order.OrderRepository;
import com.tfg.payment.infrastructure.outbound.persistence.order.entity.Order;
import com.tfg.payment.infrastructure.outbound.persistence.order.entity.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final PostgreSQLDeliveryMethodRepository deliveryMethodRepository;
    private final OrderRepository orderRepository;

    public PaymentService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper, PostgreSQLDeliveryMethodRepository deliveryMethodRepository, OrderRepository orderRepository) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.orderRepository = orderRepository;
    }

    public CartRequest createOrUpdatePaymentIntent(String cartId) throws StripeException {

        CartRequest cart = null;

        // 1. Obtenemos el JSON guardado en Redis (o null si no existe)
        String json = redisTemplate.opsForValue().get(cartId);
        if (json == null) {
            return null;
        }

        // 2. Convertimos ese JSON a CartRequest
        try {
            cart = objectMapper.readValue(json, CartRequest.class);
        } catch (IOException e) {

        }



        Long amount = calculateAmount(cart) * 100L;

        // Create PaymentIntent
        PaymentIntentCreateParams params = null;
        if(Objects.nonNull(cart.getPaymentIntentId())) {
            PaymentIntent existing = PaymentIntent.retrieve(cart.getPaymentIntentId());

            PaymentIntentUpdateParams updateIntent = PaymentIntentUpdateParams.builder()
                    .setAmount(amount)
                    .build();
            existing.update(updateIntent);
            return cart;
        }
        params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("USD")
                    .putMetadata("cartId", cartId)
                    .addPaymentMethodType("card")
                    .build();

        PaymentIntent intent = PaymentIntent.create(params);
        cart.setClientSecret(intent.getClientSecret());
        cart.setPaymentIntentId(intent.getId());
        return cart;
    }

    private Long calculateAmount(CartRequest cart) {
        DeliveryMethodResponse response = deliveryMethodRepository.findById(cart.getDeliveryMethodId());

        long amount = 0L;
        for (CartProductRequest product : cart.getItems()) {
            amount += product.price().longValue();
        }
        return response != null ? amount + response.getPrice().longValue() : amount;
    }


    /**
     * Verifica la firma del payload y construye el objeto Event de Stripe.
     *
     * @param payload       contenido bruto JSON enviado por Stripe
     * @param sigHeader     valor de la cabecera "Stripe-Signature"
     * @param endpointSecret tu clave secreta para verificar la firma del webhook
     * @return Event validado
     * @throws SignatureVerificationException si la firma no coincide
     */
    public Event constructEventFromPayload(String payload, String sigHeader, String endpointSecret)
            throws SignatureVerificationException {
        // Webhook.constructEvent lanza SignatureVerificationException si no valida correctamente.
        return Webhook.constructEvent(payload, sigHeader, endpointSecret);
    }

    /**
     * Procesa el evento de Stripe. Aquí decides qué hacer en base al tipo de evento.
     *
     * @param event evento ya parseado y validado
     */
    public void handleStripeEvent(Event event) {
        String eventType = event.getType();
        log.info("Recibido evento de Stripe: {}", eventType);

        switch (eventType) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;

            case "payment_intent.payment_failed":
                handlePaymentIntentFailed(event);
                break;

            case "charge.refunded":
                handleChargeRefunded(event);
                break;

            default:
                log.warn("Evento de Stripe no manejado: {}", eventType);
                break;
        }


    }

    private void updateOrderStatus(String paymentId, String statusString) {
        // 1) Buscar la orden por id y buyerEmail
        Order order = orderRepository
                .findByPaymentIntentId(paymentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró la orden con id: " + paymentId));

        // 2) Convertir el string al enum OrderStatus
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(statusString);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Estado inválido: " + statusString + ". Valores permitidos: " +
                            Arrays.toString(OrderStatus.values()));
        }

        // 3) Asignar el nuevo estado y guardar
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    /**
     * Maneja el caso de payment_intent.succeeded.
     * Normalmente aquí marcarías tu orden como pagada, generarías facturas, etc.
     */
    private void handlePaymentIntentSucceeded(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        if (deserializer.getObject().isPresent()) {
            PaymentIntent intent = (PaymentIntent) deserializer.getObject().get();
            String intentId = intent.getId();
            long amountReceived = intent.getAmountReceived();
            String currency = intent.getCurrency();
            String customerId = intent.getCustomer();

            log.info("PaymentIntent exitoso: id={}, amount={}, currency={}, customer={}",
                    intentId, amountReceived, currency, customerId);

            updateOrderStatus(intentId, OrderStatus.PAYMENT_RECEIVED.name());

        } else {
            log.error("No pudo deserializar el objeto PaymentIntent en payment_intent.succeeded");
        }
    }

    /**
     * Maneja el caso de payment_intent.payment_failed.
     */
    private void handlePaymentIntentFailed(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        if (deserializer.getObject().isPresent()) {
            PaymentIntent intent = (PaymentIntent) deserializer.getObject().get();
            String intentId = intent.getId();
            String lastPaymentErrorMessage = intent.getLastPaymentError() != null
                    ? intent.getLastPaymentError().getMessage()
                    : "Sin mensaje de error";

            log.warn("PaymentIntent fallido: id={}, error={}", intentId, lastPaymentErrorMessage);

            updateOrderStatus(intentId, OrderStatus.PAYMENT_FAILED.name());

        } else {
            log.error("No pudo deserializar el objeto PaymentIntent en payment_intent.payment_failed");
        }
    }

    /**
     * Maneja el caso de charge.refunded.
     */
    private void handleChargeRefunded(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        if (deserializer.getObject().isPresent()) {
            Charge charge = (Charge) deserializer.getObject().get();
            String chargeId = charge.getId();
            long amountRefunded = charge.getAmountRefunded();
            boolean fullyRefunded = charge.getAmountRefunded() == charge.getAmount();

            log.info("Cargo reembolsado: id={}, amountRefunded={}, fullyRefunded={}",
                    chargeId, amountRefunded, fullyRefunded);

            updateOrderStatus(chargeId, OrderStatus.REFUNDED.name());

        } else {
            log.error("No pudo deserializar el objeto Charge en charge.refunded");
        }
    }

    public List<DeliveryMethodResponse> deliveryMethods() {
        return deliveryMethodRepository.getDeliveryMethods();
    }
}
