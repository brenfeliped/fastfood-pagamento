package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    @Test
    void deveCriarPagamentoComStatusPENDENTE() {
        UUID pedidoId = UUID.randomUUID();
        LocalDateTime dataAtual = LocalDateTime.now();

        Pagamento pagamento = new Pagamento(
                UUID.randomUUID(),
                pedidoId,
                BigDecimal.valueOf(100),
                EnumStatusPagamento.PENDENTE,
                dataAtual,
                dataAtual
        );

        when(pagamentoRepository.save(any())).thenReturn(pagamento);

        Pagamento pagamentoSalvo = pagamentoService.salvarPagamento(pagamento);

        assertNotNull(pagamentoSalvo);
        assertEquals(EnumStatusPagamento.PENDENTE, pagamentoSalvo.getStatus());
        assertEquals(pedidoId, pagamentoSalvo.getPedidoId());

        verify(pagamentoRepository, times(1)).save(any());
    }

    @Test
    void deveListarTodosPagamentos() {
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        when(pagamentoRepository.findAll()).thenReturn(Collections.singletonList(pagamento));

        List<Pagamento> pagamentos = pagamentoService.listarTodosPagamentos();

        assertFalse(pagamentos.isEmpty());
        assertEquals(1, pagamentos.size());
        verify(pagamentoRepository).findAll();
    }

    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(id, UUID.randomUUID(), BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamento));

        Pagamento encontrado = pagamentoService.buscarPorId(id);

        assertNotNull(encontrado);
        assertEquals(id, encontrado.getId());
        verify(pagamentoRepository).findById(id);
    }

    @Test
    void deveBuscarPorPedidoId() {
        UUID pedidoId = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), pedidoId, BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        when(pagamentoRepository.findByPedidoId(pedidoId)).thenReturn(Optional.of(pagamento));

        Pagamento encontrado = pagamentoService.buscarPorPedidoId(pedidoId);

        assertNotNull(encontrado);
        assertEquals(pedidoId, encontrado.getPedidoId());
        verify(pagamentoRepository).findByPedidoId(pedidoId);
    }

    @Test
    void deveBuscarPorStatus() {
        EnumStatusPagamento status = EnumStatusPagamento.APROVADO;
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, status, LocalDateTime.now(), LocalDateTime.now());
        when(pagamentoRepository.findByStatus(status)).thenReturn(Collections.singletonList(pagamento));

        List<Pagamento> encontrados = pagamentoService.buscarPorStatus(status);

        assertFalse(encontrados.isEmpty());
        assertEquals(status, encontrados.get(0).getStatus());
        verify(pagamentoRepository).findByStatus(status);
    }
}
