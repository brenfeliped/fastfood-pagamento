package com.fastfood.infrastructure.controller.pagamento;

import com.fastfood.application.usecase.ProcessarWebhookPagamentoUseCase;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.dto.WebhookPagamentoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PagamentoWebhookResourceTest {

    private MockMvc mockMvc;

    @Mock
    private ProcessarWebhookPagamentoUseCase useCase;

    @InjectMocks
    private PagamentoWebhookResource resource;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveReceberWebhookComSucesso() throws Exception {
        WebhookPagamentoDTO dto = WebhookPagamentoDTO.builder()
                .idPagamento(UUID.randomUUID())
                .status(EnumStatusPagamento.APROVADO)
                .build();

        doNothing().when(useCase).processar(any(WebhookPagamentoDTO.class));

        mockMvc.perform(post("/api/pagamentos/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
