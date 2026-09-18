package com.vet.api.controller;

import com.vet.api.domain.veterinario.DadosAtualizacaoVeterinario;
import com.vet.api.domain.veterinario.DadosCadastroVeterinario;
import com.vet.api.domain.veterinario.DadosDetalhamentoVeterinario;
import com.vet.api.service.VeterinarioService;
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
@RequestMapping("/veterinarios")
@Tag(name = "Veterinários", description = "CRUD de veterinários")
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os veterinários")
    public ResponseEntity<List<DadosDetalhamentoVeterinario>> listar() {
        var lista = service.listar().stream().map(DadosDetalhamentoVeterinario::new).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar veterinário por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinário encontrado"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    public ResponseEntity<DadosDetalhamentoVeterinario> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(new DadosDetalhamentoVeterinario(service.buscarPorUuid(uuid)));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo veterinário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veterinário criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DadosDetalhamentoVeterinario> salvar(@RequestBody @Valid DadosCadastroVeterinario dados,
                                                               UriComponentsBuilder uriBuilder) {
        var veterinario = service.salvar(dados);
        var uri = uriBuilder.path("/veterinarios/{uuid}").buildAndExpand(veterinario.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoVeterinario(veterinario));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um veterinário")
    public ResponseEntity<DadosDetalhamentoVeterinario> atualizar(@PathVariable UUID uuid,
                                                                  @RequestBody @Valid DadosAtualizacaoVeterinario dados) {
        return ResponseEntity.ok(new DadosDetalhamentoVeterinario(service.atualizar(uuid, dados)));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir um veterinário")
    @ApiResponse(responseCode = "204", description = "Veterinário excluído")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        service.excluir(uuid);
        return ResponseEntity.noContent().build();
    }
}
