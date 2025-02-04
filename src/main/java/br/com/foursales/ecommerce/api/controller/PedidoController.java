package br.com.foursales.ecommerce.api.controller;

import br.com.foursales.ecommerce.api.dto.PedidoDTO;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import br.com.foursales.ecommerce.api.kafka.producer.PedidoProducer;
import br.com.foursales.ecommerce.api.service.PedidoService;
import br.com.foursales.ecommerce.api.utils.SecurityAuthenticationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoProducer pedidoProducer;

    @PostMapping
    @ResponseStatus(CREATED)
    public void cadastrarPedido(@Valid @RequestBody PedidoDTO pedido) throws RegraDeNegocioException, RegistroNaoEncontradoException {
        log.info("Cadastrando pedido para usuário: {}", SecurityAuthenticationUtils.getIdUsuario());
        String idPedido = service.cadastrar(pedido);
        pedidoProducer.enviarPedidoCriado(idPedido);
    }
}
