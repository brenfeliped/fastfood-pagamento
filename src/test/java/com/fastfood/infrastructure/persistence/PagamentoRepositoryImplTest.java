package com.fastfood.infrastructure.persistence;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
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
class PagamentoRepositoryImplTest {

    @Mock
    private JpaPagamentoRepository jpaPagamentoRepository;

    @InjectMocks
    private PagamentoRepositoryImpl pagamentoRepository;

    @Test
    void deveSalvarPagamento() {
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(pagamento);

        when(jpaPagamentoRepository.save(any(JpaPagamentoEntity.class))).thenReturn(entity);

        Pagamento salvo = pagamentoRepository.save(pagamento);

        assertNotNull(salvo);
        assertEquals(pagamento.getId(), salvo.getId());
    }

    @Test
    void deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(id, UUID.randomUUID(), BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(pagamento);

        when(jpaPagamentoRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Pagamento> encontrado = pagamentoRepository.findById(id);

        assertTrue(encontrado.isPresent());
        assertEquals(id, encontrado.get().getId());
    }

    @Test
    void deveRetornarVazioSeNaoEncontrarPorId() {
        UUID id = UUID.randomUUID();
        when(jpaPagamentoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Pagamento> encontrado = pagamentoRepository.findById(id);

        assertTrue(encontrado.isEmpty());
    }

    @Test
    void deveListarTodos() {
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(pagamento);

        when(jpaPagamentoRepository.findAll()).thenReturn(Collections.singletonList(entity));

        List<Pagamento> lista = pagamentoRepository.findAll();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
    }

    @Test
    void deveBuscarPorPedidoId() {
        UUID pedidoId = UUID.randomUUID();
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), pedidoId, BigDecimal.TEN, EnumStatusPagamento.PENDENTE, LocalDateTime.now(), LocalDateTime.now());
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(pagamento);

        when(jpaPagamentoRepository.findByPedidoId(pedidoId)).thenReturn(entity);

        Optional<Pagamento> encontrado = pagamentoRepository.findByPedidoId(pedidoId);

        assertTrue(encontrado.isPresent());
        assertEquals(pedidoId, encontrado.get().getPedidoId());
    }

    @Test
    void deveBuscarPorStatus() {
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.TEN, status, LocalDateTime.now(), LocalDateTime.now());
        JpaPagamentoEntity entity = JpaPagamentoEntity.fromDomain(pagamento);

        when(jpaPagamentoRepository.findByStatus(status)).thenReturn(Collections.singletonList(entity));

        List<Pagamento> lista = pagamentoRepository.findByStatus(status);

        assertFalse(lista.isEmpty());
        assertEquals(status, lista.get(0).getStatus());
    }

    @Test
    void deveDeletarPorId() {
        UUID id = UUID.randomUUID();
        doNothing().when(jpaPagamentoRepository).deleteById(id);

        pagamentoRepository.deleteById(id);

        verify(jpaPagamentoRepository).deleteById(id);
    }
}
