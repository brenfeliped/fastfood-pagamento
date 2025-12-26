package com.fastfood.bdd;


import com.fastfood.application.usecase.RegistrarPagamentoService;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.infrastructure.messaging.PagamentoProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegistrarPagamentoBDDTest {

    @Mock
    private PagamentoRepository repository;

    @Mock
    private PagamentoProducer producer;

    @InjectMocks
    private RegistrarPagamentoService service;

    @Test
    void dadoUmPedidoCriado_quandoRegistrarPagamento_entaoPagamentoEhPersistidoEEventoEnviado() {
        // Given
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.valueOf(50);

        given(repository.save(any(Pagamento.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // When
        service.executar(pedidoId, valor);

        // Then
        then(repository).should().save(any(Pagamento.class));
        verify(producer).enviarSolicitacaoPagamento(any(Pagamento.class));
    }
}
