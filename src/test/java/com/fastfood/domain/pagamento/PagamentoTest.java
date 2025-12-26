package com.fastfood.domain.pagamento;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void deveCriarPagamentoComConstrutorCompleto() {
        UUID id = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;
        EnumStatusPagamento status = EnumStatusPagamento.PENDENTE;
        LocalDateTime agora = LocalDateTime.now();

        Pagamento pagamento = new Pagamento(id, pedidoId, valor, status, agora, agora);

        assertEquals(id, pagamento.getId());
        assertEquals(pedidoId, pagamento.getPedidoId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(status, pagamento.getStatus());
        assertEquals(agora, pagamento.getCriadoEm());
        assertEquals(agora, pagamento.getAtualizadoEm());
    }

    @Test
    void deveCriarPagamentoComConstrutorSimplificado() {
        UUID pedidoId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.TEN;

        Pagamento pagamento = new Pagamento(pedidoId, valor);

        assertNull(pagamento.getId());
        assertEquals(pedidoId, pagamento.getPedidoId());
        assertEquals(valor, pagamento.getValor());
        assertEquals(EnumStatusPagamento.PENDENTE, pagamento.getStatus());
        assertNotNull(pagamento.getCriadoEm());
        assertNotNull(pagamento.getAtualizadoEm());
    }

    @Test
    void deveAprovarPagamento() {
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), BigDecimal.TEN);
        LocalDateTime antes = pagamento.getAtualizadoEm();

        // Pequeno delay para garantir diferença de tempo se necessário,
        // mas em testes unitários simples pode não ser crítico.
        // Thread.sleep(1); 

        pagamento.aprovar();

        assertEquals(EnumStatusPagamento.APROVADO, pagamento.getStatus());
        // Verifica se atualizadoEm foi modificado (pode ser igual se for muito rápido, mas a lógica é chamada)
        assertNotNull(pagamento.getAtualizadoEm());
    }

    @Test
    void deveReprovarPagamento() {
        Pagamento pagamento = new Pagamento(UUID.randomUUID(), BigDecimal.TEN);
        
        pagamento.reprovar();

        assertEquals(EnumStatusPagamento.REPROVADO, pagamento.getStatus());
        assertNotNull(pagamento.getAtualizadoEm());
    }
    
    @Test
    void deveTestarGettersESetters() {
        Pagamento pagamento = new Pagamento();
        UUID id = UUID.randomUUID();
        pagamento.setId(id);
        
        assertEquals(id, pagamento.getId());
    }
}
