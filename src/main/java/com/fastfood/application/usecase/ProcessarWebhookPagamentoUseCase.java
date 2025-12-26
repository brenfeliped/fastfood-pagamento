package com.fastfood.application.usecase;


import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.dto.WebhookPagamentoDTO;
import org.springframework.stereotype.Service;

@Service
public class ProcessarWebhookPagamentoUseCase {

    private final PagamentoService pagamentoService;
    //private final PedidoService pedidoService;

    public ProcessarWebhookPagamentoUseCase(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
        //this.pedidoService = pedidoService;
    }

    public void processar(WebhookPagamentoDTO dto) {
        Pagamento pagamento = pagamentoService.buscarPorId(dto.getIdPagamento());
        pagamento.setStatus(EnumStatusPagamento.APROVADO);
        pagamentoService.salvarPagamento(pagamento);
        System.out.println("Recebido pagamento: " + dto.getIdPagamento() + ", status: " + dto.getStatus());
    }
}
