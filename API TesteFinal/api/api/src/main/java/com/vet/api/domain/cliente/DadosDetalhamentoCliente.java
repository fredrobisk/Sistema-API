package com.vet.api.domain.cliente;

import java.util.UUID;

public record DadosDetalhamentoCliente(
        UUID uuid,
        String nome,
        String cpf,
        String email,
        String telefone
) {
    public DadosDetalhamentoCliente(Cliente cliente) {
        this(cliente.getUuid(), cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getTelefone());
    }
}