package br.com.foursales.ecommerce.api.repository;

import br.com.foursales.ecommerce.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
        FROM Produto p WHERE p.id IN ?1 AND p.qtdEstoque = 0
    """)
    boolean existsByIdInAndQtdEstoqueIsZero(List<String> idsProdutos);

    List<Produto> findAllByIdIn(List<String> idsProdutos);
}