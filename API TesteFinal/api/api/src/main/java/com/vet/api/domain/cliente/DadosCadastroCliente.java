package com.vet.api.domain.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastro de um cliente/tutor")
public record DadosCadastroCliente(
        @NotBlank @Schema(example = "Maria Silva") String nome,
        @NotBlank @Schema(example = "12345678901") String cpf,
        @NotBlank @Email @Schema(example = "maria@email.com") String email,
        @NotBlank @Schema(example = "51999999999") String telefone
) {}
