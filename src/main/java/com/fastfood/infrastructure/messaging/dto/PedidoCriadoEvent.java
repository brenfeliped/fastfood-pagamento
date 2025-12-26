package com.fastfood.infrastructure.messaging.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoCriadoEvent {

    private UUID pedidoId;
    private BigDecimal valor;


}
