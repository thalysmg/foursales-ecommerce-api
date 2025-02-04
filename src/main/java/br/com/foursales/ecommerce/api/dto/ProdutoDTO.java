package br.com.foursales.ecommerce.api.dto;

import br.com.foursales.ecommerce.api.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoDTO(
    String id,

    @NotBlank
    String nome,

    @NotBlank
    String descricao,

    @NotNull
    BigDecimal preco,

    @NotBlank
    String categoria,

    @NotNull
    Integer qtdEstoque
) {

    public Produto converterParaEntidade() {
        Produto produto = new Produto();
        atualizarEntidade(produto);
        return produto;
    }

    public void atualizarEntidade(Produto produto) {
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setQtdEstoque(qtdEstoque);
    }

    public static ProdutoDTO converterParaDTO(Produto produto) {
        return new ProdutoDTO(
            produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),
            produto.getCategoria(), produto.getQtdEstoque()
        );
    }
}
