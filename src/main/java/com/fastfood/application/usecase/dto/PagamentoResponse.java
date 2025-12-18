package com.fastfood.application.usecase.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoResponse {

    private Long id;
    private Long pedidoId;
    private BigDecimal valor;
    private EnumStatusPagamento status;
    private LocalDateTime criadoEm;
}
