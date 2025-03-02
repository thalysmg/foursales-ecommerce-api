package br.com.foursales.ecommerce.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail não pode estar em branco")
    String email,

    @NotBlank(message = "Senha não pode estar em branco")
    String senha
) {
}
