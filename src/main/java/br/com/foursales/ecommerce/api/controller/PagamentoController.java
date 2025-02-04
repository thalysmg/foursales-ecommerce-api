package br.com.foursales.ecommerce.api.controller;

import br.com.foursales.ecommerce.api.kafka.producer.PedidoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PedidoProducer pedidoProducer;

    @PutMapping
    @ResponseStatus(OK)
    public void registrarPagamento(@RequestParam("idPedido") String idPedido) {
        pedidoProducer.enviarPedidoPago(idPedido);
    }
}
