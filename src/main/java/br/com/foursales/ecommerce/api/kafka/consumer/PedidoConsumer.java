package br.com.foursales.ecommerce.api.kafka.consumer;

import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.model.Pedido;
import br.com.foursales.ecommerce.api.model.PedidoProduto;
import br.com.foursales.ecommerce.api.service.PedidoService;
import br.com.foursales.ecommerce.api.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PedidoConsumer {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    @Transactional(rollbackFor = Exception.class)
    @KafkaListener(topics = "order.paid", groupId = "foursales-ecommerce")
    public void consumirPagamentoPedido(String idPedido) throws RegistroNaoEncontradoException {
        Pedido pedido = pedidoService.buscarPorId(idPedido);
        pedido.darBaixa();
        pedidoService.salvar(pedido);
        log.info("Pagamento registrado para o pedido: {}", idPedido);
        produtoService.salvarTodos(pedido.getPedidosProdutos().stream().map(PedidoProduto::getProduto).toList());
    }
}
