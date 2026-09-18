package com.vet.api.controller;

import com.vet.api.domain.cliente.DadosAtualizacaoCliente;
import com.vet.api.domain.cliente.DadosCadastroCliente;
import com.vet.api.domain.cliente.DadosDetalhamentoCliente;
import com.vet.api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "CRUD de tutores da clínica")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    public ResponseEntity<List<DadosDetalhamentoCliente>> listar() {
        var lista = service.listar().stream().map(DadosDetalhamentoCliente::new).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar cliente por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<DadosDetalhamentoCliente> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(new DadosDetalhamentoCliente(service.buscarPorUuid(uuid)));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DadosDetalhamentoCliente> salvar(@RequestBody @Valid DadosCadastroCliente dados,
                                                           UriComponentsBuilder uriBuilder) {
        var cliente = service.salvar(dados);
        var uri = uriBuilder.path("/clientes/{uuid}").buildAndExpand(cliente.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCliente(cliente));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um cliente")
    public ResponseEntity<DadosDetalhamentoCliente> atualizar(@PathVariable UUID uuid,
                                                              @RequestBody @Valid DadosAtualizacaoCliente dados) {
        return ResponseEntity.ok(new DadosDetalhamentoCliente(service.atualizar(uuid, dados)));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir um cliente")
    @ApiResponse(responseCode = "204", description = "Cliente excluído")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        service.excluir(uuid);
        return ResponseEntity.noContent().build();
    }
}
