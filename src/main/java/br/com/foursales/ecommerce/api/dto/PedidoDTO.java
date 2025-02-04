package br.com.foursales.ecommerce.api.dto;

import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoDTO(
    @NotEmpty
    List<ProdutoQuantidadeDTO> produtosQuantidades
) {
    public List<String> getIdsProdutos() {
        return produtosQuantidades.stream().map(ProdutoQuantidadeDTO::idProduto).toList();
    }

    public int getQuantidadePorIdProduto(String id) throws RegistroNaoEncontradoException {
        return produtosQuantidades.stream()
            .filter(produtoQuantidade -> id.equals(produtoQuantidade.idProduto())).findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Erro ao obter quantidade de um ou mais produtos")).quantidade();
    }

    public record ProdutoQuantidadeDTO(

        @NotBlank(message = "Erro ao realizar pedido. Produto não informado corretamente.")
        String idProduto,
        int quantidade
    ) {

    }
}
