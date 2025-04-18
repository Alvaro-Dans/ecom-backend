package com.tfg.backend.infrastructure.inbound.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PriceRequest(
        @NotNull(message = "El valor del precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal amount,

        @NotBlank(message = "La moneda es obligatoria")
        String currency,

        @NotNull(message = "La fecha de inicio de validez es obligatoria")
        LocalDate validFrom,

        LocalDate validTo
) {
}
