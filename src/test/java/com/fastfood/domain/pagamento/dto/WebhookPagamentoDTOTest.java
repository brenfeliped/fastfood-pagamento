package com.fastfood.domain.pagamento.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebhookPagamentoDTOTest {

    @Test
    void deveCriarWebhookPagamentoDTO() {
        UUID idPagamento = UUID.randomUUID();
        EnumStatusPagamento status = EnumStatusPagamento.APROVADO;

        WebhookPagamentoDTO dto = new WebhookPagamentoDTO(idPagamento, status);

        assertEquals(idPagamento, dto.getIdPagamento());
        assertEquals(status, dto.getStatus());
    }

    @Test
    void deveTestarBuilder() {
        UUID idPagamento = UUID.randomUUID();
        EnumStatusPagamento status = EnumStatusPagamento.REPROVADO;

        WebhookPagamentoDTO dto = WebhookPagamentoDTO.builder()
                .idPagamento(idPagamento)
                .status(status)
                .build();

        assertEquals(idPagamento, dto.getIdPagamento());
        assertEquals(status, dto.getStatus());
    }

    @Test
    void deveTestarNoArgsConstructor() {
        WebhookPagamentoDTO dto = new WebhookPagamentoDTO();
        assertNotNull(dto);
    }

    @Test
    void deveTestarSetters() {
        WebhookPagamentoDTO dto = new WebhookPagamentoDTO();
        UUID idPagamento = UUID.randomUUID();
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;

        dto.setIdPagamento(idPagamento);
        dto.setStatus(status);

        assertEquals(idPagamento, dto.getIdPagamento());
        assertEquals(status, dto.getStatus());
    }
    
    @Test
    void deveTestarToString() {
        WebhookPagamentoDTO dto = WebhookPagamentoDTO.builder()
                .idPagamento(UUID.randomUUID())
                .status(EnumStatusPagamento.APROVADO)
                .build();
        assertNotNull(dto.toString());
    }
    
    @Test
    void deveTestarEqualsHashCode() {
        UUID id = UUID.randomUUID();
        WebhookPagamentoDTO dto1 = new WebhookPagamentoDTO(id, EnumStatusPagamento.APROVADO);
        WebhookPagamentoDTO dto2 = new WebhookPagamentoDTO(id, EnumStatusPagamento.APROVADO);
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
