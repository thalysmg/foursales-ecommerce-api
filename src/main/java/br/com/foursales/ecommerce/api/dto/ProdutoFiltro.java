package br.com.foursales.ecommerce.api.dto;

import java.math.BigDecimal;

public record ProdutoFiltro(String nome, String categoria, BigDecimal valorInicial, BigDecimal valorFinal) {}

