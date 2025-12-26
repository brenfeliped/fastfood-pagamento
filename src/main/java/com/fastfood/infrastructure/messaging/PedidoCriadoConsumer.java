package com.fastfood.infrastructure.messaging;

import com.fastfood.application.usecase.RegistrarPagamentoService;
import com.fastfood.infrastructure.messaging.dto.PedidoCriadoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PedidoCriadoConsumer {

    private final RegistrarPagamentoService registrar;

    public PedidoCriadoConsumer(RegistrarPagamentoService registrar) {
        this.registrar = registrar;
    }

    @KafkaListener(topics = "pedido.criado", groupId = "pagamento-group")
    public void consumir(PedidoCriadoEvent event) {
        if (event == null) return;
        log.info("PedidoCriadoConsumer -- PedidoId: {} | ValorPedido: {}", event.getPedidoId(), event.getValor());
        registrar.executar(event.getPedidoId(), event.getValor());
    }

}
