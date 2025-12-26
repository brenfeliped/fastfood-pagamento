package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.dto.WebhookPagamentoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessarWebhookPagamentoUseCaseTest {

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private ProcessarWebhookPagamentoUseCase useCase;

    @Test
    void deveProcessarPagamentoAprovado() {
        UUID pagamentoId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();

        WebhookPagamentoDTO dto = WebhookPagamentoDTO.builder()
                .idPagamento(pagamentoId)
                .status(EnumStatusPagamento.APROVADO)
                .build();

        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setPedidoId(pedidoId);

        when(pagamentoService.buscarPorId(pagamentoId)).thenReturn(pagamento);

        useCase.processar(dto);

        verify(pagamentoService).buscarPorId(pagamentoId);
        verify(pagamentoService).salvarPagamento(any(Pagamento.class));
    }

    @Test
    void deveProcessarPagamentoRecusado() {
        UUID pagamentoId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();

        WebhookPagamentoDTO dto = WebhookPagamentoDTO.builder()
                .idPagamento(pagamentoId)
                .status(EnumStatusPagamento.REPROVADO)
                .build();

        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoId);
        pagamento.setPedidoId(pedidoId);

        when(pagamentoService.buscarPorId(pagamentoId)).thenReturn(pagamento);

        useCase.processar(dto);

        verify(pagamentoService).buscarPorId(pagamentoId);
        verify(pagamentoService).salvarPagamento(any(Pagamento.class));
    }
}
