package com.fastfood.infrastructure.messaging.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PedidoCriadoEvent {

    private UUID pedidoId;
    private BigDecimal valor;

    public PedidoCriadoEvent() {}
}
