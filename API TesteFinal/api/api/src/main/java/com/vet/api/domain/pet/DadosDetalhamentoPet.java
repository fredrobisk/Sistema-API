package com.vet.api.domain.pet;

import java.util.UUID;

public record DadosDetalhamentoPet(
        UUID uuid,
        String nome,
        Especie especie,
        String raca,
        UUID clienteUuid,
        String nomeTutor
) {
    public DadosDetalhamentoPet(Pet pet) {
        this(pet.getUuid(), pet.getNome(), pet.getEspecie(), pet.getRaca(), pet.getCliente().getUuid(), pet.getCliente().getNome());
    }
}
