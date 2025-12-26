package com.fastfood.domain.pagamento;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {

    Pagamento save(Pagamento pagamento);

    Optional<Pagamento> findById(UUID id);

    List<Pagamento> findAll();

    Optional<Pagamento> findByPedidoId(UUID pedidoId);

    List<Pagamento> findByStatus(EnumStatusPagamento status);

    void deleteById(UUID id);
}
