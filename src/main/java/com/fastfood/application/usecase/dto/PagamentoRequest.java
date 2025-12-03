package com.fastfood.application.usecase.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {

    private Long pedidoId;
    private BigDecimal valor;
}
