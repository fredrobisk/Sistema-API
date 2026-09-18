package com.vet.api.controller;

import com.vet.api.domain.consulta.DadosAgendamentoConsulta;
import com.vet.api.domain.consulta.DadosAtualizacaoConsulta;
import com.vet.api.domain.consulta.DadosDetalhamentoConsulta;
import com.vet.api.service.ConsultaService;
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
@RequestMapping("/consultas")
@Tag(name = "Consultas", description = "Agendamento e gestão de consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as consultas")
    public ResponseEntity<List<DadosDetalhamentoConsulta>> listar() {
        var lista = service.listar().stream().map(DadosDetalhamentoConsulta::new).toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Buscar consulta por UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta encontrada"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<DadosDetalhamentoConsulta> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(service.buscarPorUuid(uuid)));
    }

    @PostMapping
    @Operation(summary = "Agendar uma nova consulta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Consulta agendada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados,
                                                             UriComponentsBuilder uriBuilder) {
        var consulta = service.agendar(dados);
        var uri = uriBuilder.path("/consultas/{uuid}").buildAndExpand(consulta.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoConsulta(consulta));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualizar uma consulta")
    public ResponseEntity<DadosDetalhamentoConsulta> atualizar(@PathVariable UUID uuid,
                                                               @RequestBody @Valid DadosAtualizacaoConsulta dados) {
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(service.atualizar(uuid, dados)));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Cancelar/excluir uma consulta")
    @ApiResponse(responseCode = "204", description = "Consulta excluída")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        service.excluir(uuid);
        return ResponseEntity.noContent().build();
    }
}
