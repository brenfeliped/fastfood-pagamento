package com.fastfood.domain.pagamento.exception;

import java.util.UUID;

public class PagamentoNaoEncontrado extends Exception{

    public PagamentoNaoEncontrado(UUID id){
        super("Pagemento com ID Pedido = " + id + " não encontrado.");
    }
}
