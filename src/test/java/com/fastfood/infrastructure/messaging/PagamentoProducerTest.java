package com.fastfood.infrastructure.messaging;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.infrastructure.messaging.dto.PagamentoStatusEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PagamentoProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PagamentoProducer pagamentoProducer;

    @Test
    void deveEnviarSolicitacaoPagamento() {
        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        pagamentoProducer.enviarSolicitacaoPagamento(pagamento);

        verify(kafkaTemplate).send(eq("pagamento.status-atualizado"), any(PagamentoStatusEvent.class));
    }

    @Test
    void deveEnviarPagamentoAprovado() {
        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        pagamentoProducer.enviarPagamentoAprovado(pagamento);

        verify(kafkaTemplate).send(eq("pagamento.status-atualizado"), any(PagamentoStatusEvent.class));
    }

    @Test
    void deveEnviarPagamentoReprovado() {
        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        pagamentoProducer.enviarPagamentoReprovado(pagamento);

        verify(kafkaTemplate).send(eq("pagamento.recusado"), any(PagamentoStatusEvent.class));
    }

    @Test
    void deveEnviarPagamentoCriado() {
        pagamentoProducer.enviarPagamentoCriado(new Object());
        // Método vazio, apenas garante que não lança exceção
        verifyNoInteractions(kafkaTemplate);
    }
}
