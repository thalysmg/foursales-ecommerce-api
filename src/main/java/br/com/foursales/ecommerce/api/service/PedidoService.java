package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.PedidoDTO;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import br.com.foursales.ecommerce.api.kafka.producer.PedidoProducer;
import br.com.foursales.ecommerce.api.model.Pedido;
import br.com.foursales.ecommerce.api.model.PedidoProduto;
import br.com.foursales.ecommerce.api.model.Produto;
import br.com.foursales.ecommerce.api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoService produtoService;
    private final PedidoProducer pedidoProducer;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(PedidoDTO pedidoDTO) throws RegraDeNegocioException, RegistroNaoEncontradoException {
        List<Produto> produtos = produtoService.buscarPorIds(pedidoDTO.getIdsProdutos());
        if (produtos.stream().anyMatch(produto -> produto.getQtdEstoque().equals(0))) {
            throw new RegraDeNegocioException("Não foi possível realizar o pedidio. Um ou mais produtos estão esgotados em nosso estoque");
        }
        List<PedidoProduto> pedidosProdutos = new ArrayList<>();
        for (Produto produto : produtos) {
            int qtdProduto = pedidoDTO.getQuantidadePorIdProduto(produto.getId());
            if (produto.getQtdEstoque() < qtdProduto) {
                throw new RegraDeNegocioException(
                    "Erro ao realizar pedido. Quantidade selecionada do produto " + produto.getNome() + " é maior que a quantidade disponível no estoque"
                );
            }
            pedidosProdutos.add(new PedidoProduto(produto, qtdProduto, produto.getPreco()));
        }
        Pedido pedido = repository.save(new Pedido(pedidosProdutos));
        pedidoProducer.enviarPedidoCriado(pedido.getId());
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(String idPedido) throws RegistroNaoEncontradoException {
        return repository.findById(idPedido)
            .orElseThrow(() -> new RegistroNaoEncontradoException("Pedido não encontrado"));
    }

    @Transactional
    public void salvar(Pedido pedido) {
        repository.save(pedido);
    }
}
