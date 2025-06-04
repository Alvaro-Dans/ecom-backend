package com.tfg.payment.infrastructure.inbound.rest.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.tfg.payment.application.PaymentService;
import com.tfg.payment.infrastructure.inbound.rest.dto.request.CartRequest;
import com.tfg.payment.infrastructure.inbound.rest.dto.request.CreatePaymentIntentRequest;
import com.tfg.payment.infrastructure.inbound.rest.dto.response.DeliveryMethodResponse;
import com.tfg.payment.infrastructure.inbound.rest.dto.response.PaymentIntentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{cartId}")
    public ResponseEntity<CartRequest> createOrUpdate(@PathVariable("cartId") String cartId) {
        try {
            CartRequest response = paymentService.createOrUpdatePaymentIntent(
                    cartId
            );
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint para recibir eventos de Stripe (webhook).
     * Stripe enviará un POST con el JSON del evento y la cabecera "Stripe-Signature".
     */
    @PostMapping(value = "/webhook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        String payload;
        try {
            // Leemos el body bruto (payload) para verificar la firma
            try (BufferedReader reader = request.getReader()) {
                payload = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (IOException e) {
            log.error("Error leyendo el payload del webhook de Stripe: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        Event event;
        try {
            // delegamos a PaymentService la verificación y parseo
            event = paymentService.constructEventFromPayload(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException ex) {
            log.warn("Falló la verificación de la firma de Stripe: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        // Ahora procesamos el evento según su tipo
        try {
            paymentService.handleStripeEvent(event);
        } catch (Exception ex) {
            log.error("Error procesando el evento de Stripe: {}", ex.getMessage(), ex);
            // Aun así devolvemos 200 para que Stripe no reintente infinitamente.
            return ResponseEntity.ok("");
        }

        return ResponseEntity.ok("");
    }

    @GetMapping("/delivery-methods")
    public ResponseEntity<List<DeliveryMethodResponse>> deliveryMethods() {
        return ResponseEntity.ok(paymentService.deliveryMethods());
    }
}
