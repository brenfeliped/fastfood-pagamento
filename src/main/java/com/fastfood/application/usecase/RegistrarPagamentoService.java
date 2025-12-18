package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.infrastructure.messaging.PagamentoProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class RegistrarPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoProducer producer;

    public RegistrarPagamentoService(PagamentoRepository repository, PagamentoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public Pagamento executar(UUID pedidoId, BigDecimal valor) {
        Pagamento pagamento = new Pagamento(pedidoId, valor);
        Pagamento salvo = repository.save(pagamento);

        // Publish event to request payment processing (simulated)
        producer.enviarSolicitacaoPagamento(salvo);

        return salvo;
    }
}
