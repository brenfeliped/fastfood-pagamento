package com.fastfood.domain.exception;

import java.util.UUID;

public class PagamentoNaoEncontradoException extends RuntimeException {
    public PagamentoNaoEncontradoException(UUID id) {
        super("Pagamento com ID " + id + " não encontrado.");
    }

    public static PagamentoNaoEncontradoException porPedidoId(UUID pedidoId) {
        return new PagamentoNaoEncontradoException(pedidoId) {
            @Override
            public String getMessage() {
                return "Pagamento para o Pedido ID " + pedidoId + " não encontrado.";
            }
        };
    }
}
