package com.fastfood.infrastructure.persistence;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPagamentoRepository extends JpaRepository<JpaPagamentoEntity, UUID> {

        List<JpaPagamentoEntity> findByStatus(EnumStatusPagamento status);

        JpaPagamentoEntity findByPedidoId(UUID id);

        

}
