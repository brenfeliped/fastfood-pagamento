package com.fastfood.adapters.in.resources.produtos;

import com.fastfood.application.produto.ProdutoService;
import com.fastfood.domain.produto.EnumTipoProduto;
import com.fastfood.domain.produto.Produto;
import com.fastfood.domain.produto.dto.ProdutoDTO;
import com.fastfood.domain.produto.dto.ProdutoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public class ProdutoResource {

    private final ProdutoService service;

    public ProdutoResource(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto", description = "Adiciona um novo produto ao catálogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProdutoDTO> criar(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = ProdutoMapper.toDomain(produtoDTO);
        Produto produtoSalvo = service.criar(produto);
        return ResponseEntity.status(201).body(ProdutoMapper.toDTO(produtoSalvo));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos disponíveis.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProdutoDTO>> listar() {
        List<ProdutoDTO> produtos = service.listarTodos().stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados de um produto específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> buscarPorId(@Parameter(description = "ID do produto", required = true) @PathVariable UUID id) {
        Produto produto = service.buscarPorId(id);
        return ResponseEntity.ok(ProdutoMapper.toDTO(produto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualiza os dados de um produto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoDTO> atualizar(
            @Parameter(description = "ID do produto a ser atualizado", required = true) @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do produto", required = true,
                    content = @Content(schema = @Schema(implementation = ProdutoDTO.class))
            )
            @RequestBody ProdutoDTO produtoDTO) {

        Produto produtoAtualizado = service.atualizar(id, ProdutoMapper.toDomain(produtoDTO));
        return ResponseEntity.ok(ProdutoMapper.toDTO(produtoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um produto", description = "Remove um produto do catálogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do produto a ser deletado", required = true) @PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna todos os produtos da categoria informada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Não tem produtos cadastrados dessa categoria"),
            @ApiResponse(responseCode = "400", description = "Categoria inválida"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<List<ProdutoDTO>> buscarPorCategoria(
            @Parameter(description = "Nome da categoria (ex: LANCHE, ACOMPANHAMENTO, BEBIDA)", required = true)
            @PathVariable String categoria) {
        try {
            EnumTipoProduto categoriaEnum = EnumTipoProduto.valueOf(categoria.toUpperCase());
            List<Produto> produtos = service.buscarPorCategoria(categoriaEnum);
            if (produtos == null || produtos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<ProdutoDTO> dtos = produtos.stream().map(ProdutoMapper::toDTO).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
