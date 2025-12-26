package com.fastfood.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PedidoCriadoEventTest {

    @Test
    void deveCriarPedidoCriadoEvent() {
        PedidoCriadoEvent event = new PedidoCriadoEvent();
        UUID id = UUID.randomUUID();
        event.setPedidoId(id);
        
        assertEquals(id, event.getPedidoId());
    }
    
    @Test
    void deveTestarAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        
        PedidoCriadoEvent event = new PedidoCriadoEvent(id, valor);
        
        assertEquals(id, event.getPedidoId());
        assertEquals(valor, event.getValor());
    }
    
    @Test
    void deveTestarToStringEqualsHashCode() {
        UUID id = UUID.randomUUID();
        PedidoCriadoEvent e1 = new PedidoCriadoEvent(id, BigDecimal.TEN);
        PedidoCriadoEvent e2 = new PedidoCriadoEvent(id, BigDecimal.TEN);
        
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotNull(e1.toString());
    }
}
