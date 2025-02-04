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

    @Query("""
        SELECT new br.com.foursales.ecommerce.api.dto.TicketMedioDTO(p.cliente.id, AVG(p.valorTotal))
        FROM Pedido p GROUP BY p.cliente.id
    """)
    List<TicketMedioDTO> findTicketMediosUsuarios();

    @Query("""
        SELECT new br.com.foursales.ecommerce.api.dto.ClienteValorTotalDTO(p.cliente.id, SUM(p.valorTotal))
        FROM Pedido p
        GROUP BY p.cliente.id 
        ORDER BY SUM(p.valorTotal) DESC
    """)
    List<ClienteValorTotalDTO> findTop5UsuariosQueMaisCompraram(Pageable pageable);

    @Query("""
        SELECT SUM(p.valorTotal) FROM Pedido p WHERE p.data >= ?1 AND p.data <= ?2
    """)
    BigDecimal findFaturamentoMensal(LocalDateTime dataInicio, LocalDateTime dataFim);
}