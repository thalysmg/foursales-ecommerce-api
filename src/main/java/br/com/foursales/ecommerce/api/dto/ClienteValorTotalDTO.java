package br.com.foursales.ecommerce.api.dto;

import java.math.BigDecimal;

public record ClienteValorTotalDTO(
    String cliente,
    BigDecimal valorTotal
) {
}
