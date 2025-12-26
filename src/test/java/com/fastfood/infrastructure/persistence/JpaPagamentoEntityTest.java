package com.fastfood.infrastructure.persistence;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JpaPagamentoEntityTest {

    @Test
    void deveCriarEntity() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        LocalDateTime agora = LocalDateTime.now();

        JpaPagamentoEntity entity = new JpaPagamentoEntity(id, pedidoId, valor, status, agora, agora);

        assertEquals(id, entity.getId());
        assertEquals(pedidoId, entity.getPedidoId());
        assertEquals(valor, entity.getValor());
        assertEquals(status, entity.getStatus());
        assertEquals(agora, entity.getCriadoEm());
        assertEquals(agora, entity.getAtualizadoEm());
    }

    @Test
    void deveConverterParaDomain() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        LocalDateTime agora = LocalDateTime.now();

        JpaPagamentoEntity entity = new JpaPagamentoEntity(id, pedidoId, valor, status, agora, agora);
        Pagamento domain = entity.toDomain();

        assertEquals(id, domain.getId());
        assertEquals(pedidoId, domain.getPedidoId());
        assertEquals(valor, domain.getValor());
        assertEquals(status, domain.getStatus());
        assertEquals(agora, domain.getCriadoEm());
        assertEquals(agora, domain.getAtualizadoEm());
    }

    @Test
    void deveCriarDeDomain() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        LocalDateTime agora = LocalDateTime.now();

        Pagamento domain = new Pagamento(id, pedidoId, valor, status, agora, agora);
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(domain);

        assertEquals(id, entity.getId());
        assertEquals(pedidoId, entity.getPedidoId());
        assertEquals(valor, entity.getValor());
        assertEquals(status, entity.getStatus());
        assertEquals(agora, entity.getCriadoEm());
        assertEquals(agora, entity.getAtualizadoEm());
    }
    
    @Test
    void deveTestarNoArgsConstructor() {
        JpaPagamentoEntity entity = new JpaPagamentoEntity();
        assertNotNull(entity);
    }
    
    @Test
    void deveTestarSetters() {
        JpaPagamentoEntity entity = new JpaPagamentoEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        assertEquals(id, entity.getId());
    }
}
