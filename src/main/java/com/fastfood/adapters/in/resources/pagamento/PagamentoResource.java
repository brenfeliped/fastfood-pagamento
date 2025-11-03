package com.fastfood.adapters.in.resources.pagamento;


import com.fastfood.application.pagamento.PagamentoService;
import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.dto.PagamentoDTO;
import com.fastfood.domain.pagamento.dto.PagamentoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoResource {

    private final PagamentoService pagamentoService;

    public PagamentoResource(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }


    @PostMapping
    @Operation(summary = "Criar novo pagamento", description = "Cria um novo pagamento com os dados fornecidos.(id pode ir vazio)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do pagamento inválidos")
    })
    public Pagamento criarPagamento(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do pagamento a ser criado", required = true)@RequestBody PagamentoDTO pagamentoDTO){
        Pagamento pagamento = PagamentoMapper.toDomain(pagamentoDTO);
        pagamento.setId(UUID.randomUUID());
        Pagamento pagamentoSalvo = pagamentoService.salvarPagamento(pagamento);
        return pagamentoService.salvarPagamento(pagamentoSalvo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID", description = "Retorna os dados do pagamento com o ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable UUID id) {
        Pagamento pagamento = pagamentoService.buscarPorId(id);

        if(pagamento == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pagamento);
    }



    @GetMapping("pedidoid/{id}")
    @Operation(summary = "Buscar pagamento por ID do pedido", description = "Retorna os dados do pagamento com o ID do pedido fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<Pagamento> buscarPorPedidoId(@PathVariable UUID id) {
        Pagamento pagamento = pagamentoService.buscarPorPedidoId(id);

        if(pagamento.equals(null)){
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(pagamento);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar pagamentos por status", description = "Lista todos os pagamentos com o status informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    public ResponseEntity<List<Pagamento>> buscarPorStatus(@Parameter(description = "Status do pagamento", required = true) @PathVariable EnumStatusPagamento status) {

        return ResponseEntity.ok(pagamentoService.buscarPorStatus(status));
    }


}
