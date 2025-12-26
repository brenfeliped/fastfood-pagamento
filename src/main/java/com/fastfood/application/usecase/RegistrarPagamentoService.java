package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.infrastructure.messaging.PagamentoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
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
        pagamento.setAtualizadoEm(LocalDateTime.now());
        pagamento.setCriadoEm(pagamento.getAtualizadoEm());
        Pagamento salvo = repository.save(pagamento);
        log.info("RegistrarPagamentoService -- PagamentoId: {}, PedidoId: {},  Status {}", pagamento.getId(), pagamento.getPedidoId(), pagamento.getStatus());
        // Publish event to request payment processing (simulated)
        producer.enviarSolicitacaoPagamento(salvo);

        return salvo;
    }
}
