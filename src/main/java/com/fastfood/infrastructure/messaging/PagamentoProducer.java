package com.fastfood.infrastructure.messaging;

import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.infrastructure.messaging.dto.PagamentoStatusEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoProducer {

    private final KafkaTemplate<String, Object> kafka;

    public PagamentoProducer(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void enviarSolicitacaoPagamento(Pagamento pagamento) {
        // For simplicity we send a status event AGUARDANDO_PAGAMENTO to topic 'pagamento.solicitado'
        PagamentoStatusEvent e = toEvent(pagamento);
        kafka.send("pagamento.solicitado", e);
    }

    public void enviarPagamentoAprovado(Pagamento pagamento) {
        PagamentoStatusEvent e = toEvent(pagamento);
        kafka.send("pagamento.aprovado", e);
    }

    public void enviarPagamentoReprovado(Pagamento pagamento) {
        PagamentoStatusEvent e = toEvent(pagamento);
        kafka.send("pagamento.recusado", e);
    }

    private PagamentoStatusEvent toEvent(Pagamento pagamento) {
        PagamentoStatusEvent event = new PagamentoStatusEvent();
        event.setPagamentoId(pagamento.getId());
        event.setPedidoId(pagamento.getPedidoId());
        event.setStatus(pagamento.getStatus());
        event.setValor(pagamento.getValor());
        event.setAtualizadoEm(pagamento.getAtualizadoEm());
        return event;
    }
}
