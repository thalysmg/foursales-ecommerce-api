package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.PedidoDTO;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import br.com.foursales.ecommerce.api.model.Pedido;
import br.com.foursales.ecommerce.api.model.PedidoProduto;
import br.com.foursales.ecommerce.api.model.Produto;
import br.com.foursales.ecommerce.api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(PedidoDTO pedidoDTO) throws RegraDeNegocioException, RegistroNaoEncontradoException {
        List<Produto> produtos = produtoService.buscarPorIds(pedidoDTO.getIdsProdutos());
        if (produtos.stream().anyMatch(produto -> produto.getQtdEstoque().equals(0))) {
            throw new RegraDeNegocioException("Não foi possível realizar o pedidio. Um ou mais produtos estão esgotados em nosso estoque");
        }
        List<PedidoProduto> pedidosProdutos = new ArrayList<>();
        for (Produto produto : produtos) {
            int qtdProduto = pedidoDTO.getQuantidadePorIdProduto(produto.getId());
            pedidosProdutos.add(new PedidoProduto(produto, qtdProduto, produto.getPreco()));
        }
        repository.save(new Pedido(pedidosProdutos));
    }
}
