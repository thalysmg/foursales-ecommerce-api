package br.com.foursales.ecommerce.api.model.esDocument;

import br.com.foursales.ecommerce.api.model.Produto;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "produto")
public class ProdutoElastic {

    @Id
    private String id;
    private String nome;
    private String categoria;
    private Integer qtdEstoque;
    private BigDecimal preco;

    public ProdutoElastic(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.qtdEstoque = produto.getQtdEstoque();
        this.preco = produto.getPreco();
    }

    public void atualizar(Produto produto) {
        if (isNull(produto)) return;

        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.qtdEstoque = produto.getQtdEstoque();
        this.preco = produto.getPreco();
    }
}
