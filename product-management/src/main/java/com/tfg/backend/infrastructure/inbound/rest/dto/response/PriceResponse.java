package com.tfg.backend.infrastructure.inbound.rest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PriceResponse(
        String id,
        BigDecimal amount,
        String currency,
        LocalDate validFrom,
        LocalDate validTo
) {
}
