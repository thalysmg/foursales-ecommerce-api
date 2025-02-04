package br.com.foursales.ecommerce.api.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void enviarPedidoCriado(String idPedido) {
        log.info("Enviado evento de criação para o pedido: {}", idPedido);
        kafkaTemplate.send("order.created", idPedido);
    }

    public void enviarPedidoPago(String idPedido) {
        log.info("Enviado evento de pagamento para o pedido: {}", idPedido);
        kafkaTemplate.send("order.paid", idPedido);
    }
}
