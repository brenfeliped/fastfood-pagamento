package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.domain.pagamento.exception.PagamentoNaoEncontrado;
import com.fastfood.infrastructure.messaging.PagamentoProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AtualizarStatusPagamentoService {

    private final PagamentoRepository repository;
    private final PagamentoProducer producer;

    public AtualizarStatusPagamentoService(PagamentoRepository repository, PagamentoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public void aprovar(UUID pedidoId) throws PagamentoNaoEncontrado {
        Pagamento pagamento = repository.findByPedidoId(pedidoId).get();
        if(pagamento != null) {
            pagamento.aprovar();
            repository.save(pagamento);
            producer.enviarPagamentoAprovado(pagamento);
        } else {
            throw new PagamentoNaoEncontrado(pedidoId);
        }
    }

    @Transactional
    public void recusar(UUID pedidoId) throws PagamentoNaoEncontrado {
        Pagamento pagamento = repository.findByPedidoId(pedidoId).get();
        if(pagamento != null) {
            pagamento.reprovar();
            repository.save(pagamento);
            producer.enviarPagamentoReprovado(pagamento);
        }else {
            throw new PagamentoNaoEncontrado(pedidoId);
        }

    }
}
