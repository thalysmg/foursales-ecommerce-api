package br.com.foursales.ecommerce.api.repository;

import br.com.foursales.ecommerce.api.dto.ClienteValorTotalDTO;
import br.com.foursales.ecommerce.api.dto.TicketMedioDTO;
import br.com.foursales.ecommerce.api.model.Pedido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, String> {

    default List<TicketMedioDTO> findTicketMediosUsuarios() {
        return findTicketMediosUsuariosRaw().stream()
            .map(result -> new TicketMedioDTO((String) result[0], (BigDecimal) result[1]))
            .toList();
    }

    @Query(value = """
        SELECT p.id_cliente as cliente, AVG(p.valor_total) as valorMedio
        FROM pedido p
        WHERE p.situacao = 'PAGO'
        GROUP BY p.id_cliente
    """, nativeQuery = true)
    List<Object[]> findTicketMediosUsuariosRaw();

    default List<ClienteValorTotalDTO> findTop5ClientesQueMaisCompraram() {
        return findTop5ClientesQueMaisCompraramRaw().stream()
            .map(result -> new ClienteValorTotalDTO((String) result[0], (BigDecimal) result[1]))
            .toList();
    }

    @Query(value = """
        SELECT p.id_cliente as cliente, SUM(p.valor_total) as valorTotal
        FROM pedido p
        WHERE p.situacao = 'PAGO'
        GROUP BY p.id_cliente
        ORDER BY SUM(p.valor_total) DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> findTop5ClientesQueMaisCompraramRaw();

    @Query(value = """
        SELECT SUM(p.valor_total)
        FROM pedido p
        WHERE p.data >= ?1 AND p.data <= ?2 AND p.situacao = 'PAGO'
    """, nativeQuery = true)
    BigDecimal findFaturamentoMensal(LocalDateTime dataInicio, LocalDateTime dataFim);
}