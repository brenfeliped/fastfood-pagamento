package com.fastfood.domain.pagamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    private UUID id;
    private UUID pedidoId;
    private BigDecimal valor;
    private EnumStatusPagamento status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Pagamento(UUID pedidoId, BigDecimal valor) {
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.status = EnumStatusPagamento.PENDENTE;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    public void aprovar() {
        this.status = EnumStatusPagamento.APROVADO;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void reprovar() {
        this.status = EnumStatusPagamento.REPROVADO;
        this.atualizadoEm = LocalDateTime.now();
    }

}
