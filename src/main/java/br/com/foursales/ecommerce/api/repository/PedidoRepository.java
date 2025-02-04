package br.com.foursales.ecommerce.api.repository;

import br.com.foursales.ecommerce.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
}