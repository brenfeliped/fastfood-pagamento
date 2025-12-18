package com.fastfood.infrastructure.messaging.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PagamentoStatusEvent {

    private UUID pagamentoId;
    private UUID pedidoId;
    private EnumStatusPagamento status;
    private BigDecimal valor;
    private LocalDateTime atualizadoEm;

    public PagamentoStatusEvent() {}
}
