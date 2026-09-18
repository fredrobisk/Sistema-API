package com.vet.api.domain.veterinario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoVeterinario(
        @NotBlank String nome,
        @NotBlank @Email String email
) {}
