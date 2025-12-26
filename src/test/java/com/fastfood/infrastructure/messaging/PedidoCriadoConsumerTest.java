package com.fastfood.infrastructure.messaging;

import com.fastfood.application.usecase.RegistrarPagamentoService;
import com.fastfood.infrastructure.messaging.dto.PedidoCriadoEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class PedidoCriadoConsumerTest {

    @Mock
    private RegistrarPagamentoService registrarPagamentoService;

    @InjectMocks
    private PedidoCriadoConsumer pedidoCriadoConsumer;

    @Test
    void deveConsumirEventoPedidoCriado() {
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        PedidoCriadoEvent event = new PedidoCriadoEvent();
        event.setPedidoId(pedidoId);
        event.setValor(valor);

        pedidoCriadoConsumer.consumir(event);

        verify(registrarPagamentoService).executar(pedidoId, valor);
    }

    @Test
    void naoDeveFazerNadaSeEventoForNulo() {
        pedidoCriadoConsumer.consumir(null);

        verifyNoInteractions(registrarPagamentoService);
    }
}
