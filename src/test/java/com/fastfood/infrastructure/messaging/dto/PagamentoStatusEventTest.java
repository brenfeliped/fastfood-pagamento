package com.fastfood.infrastructure.messaging.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PagamentoStatusEventTest {

    @Test
    void deveCriarPagamentoStatusEvent() {
        PagamentoStatusEvent event = new PagamentoStatusEvent();
        UUID id = UUID.randomUUID();
        event.setPagamentoId(id);
        
        assertEquals(id, event.getPagamentoId());
    }
    
    @Test
    void deveTestarAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.APROVADO;
        LocalDateTime agora = LocalDateTime.now();
        
        PagamentoStatusEvent event = new PagamentoStatusEvent(id, pedidoId,   valor, status, agora);
        
        assertEquals(id, event.getPagamentoId());
        assertEquals(pedidoId, event.getPedidoId());
        assertEquals(valor, event.getValor());
        assertEquals(status, event.getStatus());
        assertEquals(agora, event.getAtualizadoEm());
    }
    
    @Test
    void deveTestarToStringEqualsHashCode() {
        UUID id = UUID.randomUUID();
        PagamentoStatusEvent e1 = new PagamentoStatusEvent();
        e1.setPagamentoId(id);
        PagamentoStatusEvent e2 = new PagamentoStatusEvent();
        e2.setPagamentoId(id);
        
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotNull(e1.toString());
    }
}
