package com.fastfood.domain.pagamento.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoMapperTest {

    @Test
    void deveMapearParaDTO() {
        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                EnumStatusPagamento.APROVADO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        PagamentoDTO dto = PagamentoMapper.toDTO(pagamento);

        assertNotNull(dto);
        assertEquals(pagamento.getId(), dto.getId());
        assertEquals(pagamento.getPedidoId(), dto.getPedidoId());
        assertEquals(pagamento.getValor(), dto.getValor());
        assertEquals(pagamento.getStatus(), dto.getStatus());
    }

    @Test
    void deveRetornarNullAoMapearParaDTOComEntradaNula() {
        assertNull(PagamentoMapper.toDTO(null));
    }

    @Test
    void deveMapearParaDomain() {
        PagamentoDTO dto = new PagamentoDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Pagamento pagamento = PagamentoMapper.toDomain(dto);

        assertNotNull(pagamento);
        assertEquals(dto.getId(), pagamento.getId());
        assertEquals(dto.getPedidoId(), pagamento.getPedidoId());
        assertEquals(dto.getValor(), pagamento.getValor());
        assertEquals(EnumStatusPagamento.PENDENTE, pagamento.getStatus());
    }

    @Test
    void deveRetornarNullAoMapearParaDomainComEntradaNula() {
        assertNull(PagamentoMapper.toDomain(null));
    }
}
