package br.com.foursales.ecommerce.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioDTO(
    List<TicketMedioDTO> ticketsMedios,
    List<ClienteValorTotalDTO> clientesValorTotalTop5,
    BigDecimal faturamentoMensal
) {
}
