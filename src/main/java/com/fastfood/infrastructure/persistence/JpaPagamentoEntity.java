package com.fastfood.infrastructure.persistence;


import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaPagamentoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

//    @NotNull
//    private UUID pedidoId;
    @Column(name = "pedido_id", nullable = false, unique = true)
    private UUID pedidoId;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private EnumStatusPagamento status;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;


    public Pagamento toDomain(){
        return new Pagamento(this.id, this.pedidoId, this.valor, this.status, this.criadoEm, this.atualizadoEm);
    }

    public static JpaPagamentoEntity fromDomain(Pagamento pagamento){
        JpaPagamentoEntity pagamentoEntity = new JpaPagamentoEntity();
        pagamentoEntity.setId(pagamento.getId());
        pagamentoEntity.setPedidoId(pagamento.getPedidoId());
        pagamentoEntity.setStatus(pagamento.getStatus());
        return  pagamentoEntity;
    }
}
