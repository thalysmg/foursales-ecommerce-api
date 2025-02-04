package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.ClienteValorTotalDTO;
import br.com.foursales.ecommerce.api.dto.RelatorioDTO;
import br.com.foursales.ecommerce.api.dto.TicketMedioDTO;
import br.com.foursales.ecommerce.api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static java.time.LocalTime.MAX;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public RelatorioDTO listarRelatorio() {
        List<TicketMedioDTO> ticketsMedios = pedidoRepository.findTicketMediosUsuarios();
        List<ClienteValorTotalDTO> clientesValorTotalTop5 = pedidoRepository.findTop5UsuariosQueMaisCompraram(Pageable.ofSize(5));
        YearMonth mes = YearMonth.from(LocalDate.now());
        LocalDateTime dataInicio = mes.atDay(1).atStartOfDay();
        LocalDateTime dataFim = mes.atEndOfMonth().atTime(MAX);
        BigDecimal totalFaturadoNoMes = pedidoRepository.findFaturamentoMensal(dataInicio, dataFim);
        return new RelatorioDTO(ticketsMedios, clientesValorTotalTop5, totalFaturadoNoMes);
    }
}
