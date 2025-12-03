package com.fastfood.domain.pagamento.dto;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private UUID id;
    private UUID pedidoId;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private EnumStatusPagamento status;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
