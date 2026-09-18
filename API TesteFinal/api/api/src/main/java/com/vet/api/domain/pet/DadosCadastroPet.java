package com.vet.api.domain.pet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Dados para cadastro de um pet")
public record DadosCadastroPet(
        @NotBlank @Schema(example = "Thor") String nome,
        @NotNull Especie especie,
        @Schema(example = "Labrador") String raca,
        @NotNull @Schema(description = "UUID do cliente tutor") UUID clienteUuid
) {}
