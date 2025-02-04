package br.com.foursales.ecommerce.api.repository;

import br.com.foursales.ecommerce.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, String> {
}