package com.vet.api.controller;

import com.vet.api.domain.pet.DadosAtualizacaoPet;
import com.vet.api.domain.pet.DadosCadastroPet;
import com.vet.api.domain.pet.DadosDetalhamentoPet;
import com.vet.api.service.PetService;
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
@RequestMapping("/pets")
@Tag(name = "Pets", description = "CRUD de animais da clínica")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os pets")
    public ResponseEntity<List<DadosDetalhamentoPet>> listar() {
        var lista = service.listar().stream().map(DadosDetalhamentoPet::new).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar pet por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet encontrado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public ResponseEntity<DadosDetalhamentoPet> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(new DadosDetalhamentoPet(service.buscarPorUuid(uuid)));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo pet")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pet criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DadosDetalhamentoPet> salvar(@RequestBody @Valid DadosCadastroPet dados,
                                                       UriComponentsBuilder uriBuilder) {
        var pet = service.salvar(dados);
        var uri = uriBuilder.path("/pets/{uuid}").buildAndExpand(pet.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPet(pet));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar um pet")
    public ResponseEntity<DadosDetalhamentoPet> atualizar(@PathVariable UUID uuid,
                                                          @RequestBody @Valid DadosAtualizacaoPet dados) {
        return ResponseEntity.ok(new DadosDetalhamentoPet(service.atualizar(uuid, dados)));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir um pet")
    @ApiResponse(responseCode = "204", description = "Pet excluído")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        service.excluir(uuid);
        return ResponseEntity.noContent().build();
    }
}
