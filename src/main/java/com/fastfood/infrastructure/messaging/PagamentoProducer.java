package com.fastfood.infrastructure.messaging;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.infrastructure.messaging.dto.PagamentoStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PagamentoProducer {

    private final KafkaTemplate<String, Object> kafka;

    public PagamentoProducer(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    public void enviarSolicitacaoPagamento(Pagamento pagamento) {
        //  PENDENTE to topic 'pagamento.solicitado'
        PagamentoStatusEvent e = toEvent(pagamento);
        kafka.send("pagamento.status-atualizado", e);
        log.info("enviarSolicitacaoPagamento -- Pagamento adicionado a fila pagamento.solicitado -- PagamentoId {} | PedidoId {} | Valor {} ", pagamento.getId(), pagamento.getPedidoId(), pagamento.getValor());
    }

    public void enviarPagamentoAprovado(Pagamento pagamento) {
        PagamentoStatusEvent e = toEvent(pagamento);
        pagamento.setStatus(EnumStatusPagamento.APROVADO);
        kafka.send("pagamento.status-atualizado", e);
    }

    public void enviarPagamentoReprovado(Pagamento pagamento) {
        PagamentoStatusEvent e = toEvent(pagamento);
        kafka.send("pagamento.recusado", e);
        log.info("enviarSolicitacaoPagamento -- Pagamento adicionado a fila pagamento.recusado -- PagamentoId {} | PedidoId {} | Valor {} ", pagamento.getId(), pagamento.getPedidoId(), pagamento.getValor());
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

    public void enviarPagamentoCriado(Object any) {

    }
}
