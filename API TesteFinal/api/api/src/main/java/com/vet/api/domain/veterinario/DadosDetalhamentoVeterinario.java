package com.vet.api.domain.veterinario;

import java.util.UUID;

public record DadosDetalhamentoVeterinario(
        UUID uuid,
        String nome,
        String email
) {
    public DadosDetalhamentoVeterinario(Veterinario veterinario) {
        this(veterinario.getUuid(), veterinario.getNome(), veterinario.getEmail());
    }
}