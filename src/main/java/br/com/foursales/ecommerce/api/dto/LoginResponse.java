package br.com.foursales.ecommerce.api.dto;

import java.time.LocalDateTime;

public record LoginResponse(
    String token,
    LocalDateTime dataExpiracao
) {
}
