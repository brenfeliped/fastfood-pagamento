package com.fastfood.infrastructure.messaging.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PagamentoStatusEvent {

    private UUID pagamentoId;
    private UUID pedidoId;
    private BigDecimal valor;
    private EnumStatusPagamento status;
    private LocalDateTime atualizadoEm;

}
