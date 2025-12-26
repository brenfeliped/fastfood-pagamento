package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import com.fastfood.domain.pagamento.exception.PagamentoNaoEncontrado;
import com.fastfood.infrastructure.messaging.PagamentoProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarStatusPagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private AtualizarStatusPagamentoService service;

    @Mock
    private PagamentoProducer producer;

    @Test
    void deveAprovarPagamento() throws PagamentoNaoEncontrado {
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime dataAtual = LocalDateTime.now();

        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                pedidoId,
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                dataAtual,
                dataAtual
        );

        when(pagamentoRepository.findByPedidoId(pedidoId))
                .thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.aprovar(pedidoId);

        assertEquals(EnumStatusPagamento.APROVADO, pagamento.getStatus());
        verify(pagamentoRepository).save(pagamento);
        verify(producer).enviarPagamentoAprovado(pagamento);
    }

    @Test
    void deveRecusarPagamento() throws PagamentoNaoEncontrado {
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime dataAtual = LocalDateTime.now();

        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                pedidoId,
                BigDecimal.TEN,
                EnumStatusPagamento.PENDENTE,
                dataAtual,
                dataAtual
        );

        when(pagamentoRepository.findByPedidoId(pedidoId))
                .thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.recusar(pedidoId);

        assertEquals(EnumStatusPagamento.REPROVADO, pagamento.getStatus());
        verify(pagamentoRepository).save(pagamento);
        verify(producer).enviarPagamentoReprovado(pagamento);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontradoAoAprovar() {
        UUID pedidoId = UUID.randomUUID();

        when(pagamentoRepository.findByPedidoId(pedidoId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.aprovar(pedidoId));
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontradoAoRecusar() {
        UUID pedidoId = UUID.randomUUID();

        when(pagamentoRepository.findByPedidoId(pedidoId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.recusar(pedidoId));
    }
    
    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontradoAoAprovarComOptionalPresenteMasNulo() {
        
        UUID pedidoId = UUID.randomUUID();
        when(pagamentoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> service.aprovar(pedidoId));
    }
}
