package com.fastfood.application.usecase;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrarPagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @Mock
    private PagamentoProducer producer;

    @InjectMocks
    private RegistrarPagamentoService service;

    @Test
    void deveRegistrarPagamentoComSucesso() {
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        
        Pagamento pagamentoSalvo = new Pagamento(pedidoId, valor);
        pagamentoSalvo.setId(UUID.randomUUID());
        
        when(repository.save(any(Pagamento.class))).thenReturn(pagamentoSalvo);

        Pagamento resultado = service.executar(pedidoId, valor);

        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getPedidoId());
        assertEquals(valor, resultado.getValor());

        verify(repository).save(any(Pagamento.class));
        verify(producer).enviarSolicitacaoPagamento(any());
    }
}
