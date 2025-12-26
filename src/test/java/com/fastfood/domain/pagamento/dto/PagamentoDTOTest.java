package com.fastfood.domain.pagamento.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PagamentoDTOTest {

    @Test
    void deveCriarPagamentoDTO() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        LocalDateTime agora = LocalDateTime.now();

        PagamentoDTO dto = new PagamentoDTO(id, pedidoId, valor, status, agora, agora);

        assertEquals(id, dto.getId());
        assertEquals(pedidoId, dto.getPedidoId());
        assertEquals(valor, dto.getValor());
        assertEquals(status, dto.getStatus());
        assertEquals(agora, dto.getCriadoEm());
        assertEquals(agora, dto.getAtualizadoEm());
    }

    @Test
    void deveTestarNoArgsConstructor() {
        PagamentoDTO dto = new PagamentoDTO();
        assertNotNull(dto);
    }

    @Test
    void deveTestarSetters() {
        PagamentoDTO dto = new PagamentoDTO();
        UUID id = UUID.randomUUID();
        dto.setId(id);
        assertEquals(id, dto.getId());
    }
    
    @Test
    void deveTestarToString() {
        PagamentoDTO dto = new PagamentoDTO();
        assertNotNull(dto.toString());
    }
    
    @Test
    void deveTestarEqualsHashCode() {
        UUID id = UUID.randomUUID();
        PagamentoDTO dto1 = new PagamentoDTO();
        dto1.setId(id);
        PagamentoDTO dto2 = new PagamentoDTO();
        dto2.setId(id);
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
