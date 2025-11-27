package com.fastfood.domain.pagamento;


import java.util.List;
import java.util.UUID;

public interface PagamentoRepository {

    Pagamento save(Pagamento pagamento);

    Pagamento findById(UUID id);

    List<Pagamento> findAll();

    Pagamento findByPedidoId(UUID pedidoId);

    List<Pagamento> findByStatus(EnumStatusPagamento status);

    void deleteById(UUID id);
}
