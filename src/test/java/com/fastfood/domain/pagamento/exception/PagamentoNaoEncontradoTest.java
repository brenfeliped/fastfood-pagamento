package com.fastfood.domain.pagamento.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoNaoEncontradoTest {

    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        UUID id = UUID.randomUUID();
        PagamentoNaoEncontrado exception = new PagamentoNaoEncontrado(id);

        assertEquals("Pagemento com ID Pedido = " + id + " não encontrado.", exception.getMessage());
    }
}
