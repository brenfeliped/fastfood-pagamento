package com.fastfood.infrastructure.controller.pagamento;

import com.fastfood.application.usecase.PagamentoService;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.dto.PagamentoDTO;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PagamentoResourceTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoResource pagamentoResource;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoResource).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void deveCriarPagamento() throws Exception {
        UUID pedidoId = UUID.randomUUID();
        PagamentoDTO dto = new PagamentoDTO();
        dto.setPedidoId(pedidoId);
        dto.setValor(BigDecimal.TEN);

        Pagamento pagamento = new Pagamento(pedidoId, BigDecimal.TEN);
        pagamento.setId(UUID.randomUUID());

        when(pagamentoService.salvarPagamento(any(Pagamento.class))).thenReturn(pagamento);

        mockMvc.perform(post("/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pedidoId").value(pedidoId.toString()));
    }

    @Test
    void deveBuscarPagamentoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);

        when(pagamentoService.buscarPorId(id)).thenReturn(pagamento);

        mockMvc.perform(get("/api/pagamentos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoPagamentoNaoExistirPorId() throws Exception {
        UUID id = UUID.randomUUID();
        when(pagamentoService.buscarPorId(id)).thenReturn(null);

        mockMvc.perform(get("/api/pagamentos/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveBuscarPagamentoPorPedidoId() throws Exception {
        UUID pedidoId = UUID.randomUUID();
        Pagamento pagamento = new Pagamento();
        pagamento.setPedidoId(pedidoId);

        when(pagamentoService.buscarPorPedidoId(pedidoId)).thenReturn(pagamento);

        mockMvc.perform(get("/api/pagamentos/pedidoid/{id}", pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pedidoId").value(pedidoId.toString()));
    }

    @Test
    void deveRetornarNotFoundQuandoPagamentoNaoExistirPorPedidoId() throws Exception {
        UUID pedidoId = UUID.randomUUID();
        when(pagamentoService.buscarPorPedidoId(pedidoId)).thenReturn(null);

        mockMvc.perform(get("/api/pagamentos/pedidoid/{id}", pedidoId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveBuscarPagamentoPorStatus() throws Exception {
        EnumStatusPagamento statusPagamento = EnumStatusPagamento.APROVADO;
        Pagamento pagamento = new Pagamento();
        pagamento.setStatus(statusPagamento);

        when(pagamentoService.buscarPorStatus(statusPagamento)).thenReturn(Collections.singletonList(pagamento));

        mockMvc.perform(get("/api/pagamentos/status/{status}", statusPagamento))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value(statusPagamento.toString()));
    }
}
