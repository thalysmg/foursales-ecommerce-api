package br.com.foursales.ecommerce.api.dto;

import java.math.BigDecimal;

public record TicketMedioDTO(
    String cliente,
    BigDecimal valorMedio
) {
}
