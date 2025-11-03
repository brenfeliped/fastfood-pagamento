package com.fastfood.adapters.in.resources.pagamento;

import com.fastfood.application.pagamento.ProcessarWebhookPagamentoUseCase;
import com.fastfood.domain.pagamento.dto.WebhookPagamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos/webhook")
public class PagamentoWebhookResource {

    private final ProcessarWebhookPagamentoUseCase useCase;

    public PagamentoWebhookResource(ProcessarWebhookPagamentoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> receberWebhook(@RequestBody WebhookPagamentoDTO payload) {
        useCase.processar(payload); // lógica de atualização de pedido
        return ResponseEntity.ok().build(); // 200 OK
    }
}
