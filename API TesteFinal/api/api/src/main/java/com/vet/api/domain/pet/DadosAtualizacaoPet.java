package com.vet.api.domain.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPet(
        @NotBlank String nome,
        @NotNull Especie especie,
        String raca
) {}
