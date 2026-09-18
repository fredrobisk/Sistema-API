package com.vet.api.domain.veterinario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastro de um veterinário")
public record DadosCadastroVeterinario(
        @NotBlank @Schema(example = "Dr. João Souza") String nome,
        @NotBlank @Email @Schema(example = "joao@clinica.com") String email
) {}
