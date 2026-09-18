package com.vet.api.domain.consulta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para agendar uma consulta")
public record DadosAgendamentoConsulta(
        @NotNull UUID petUuid,
        @NotNull UUID veterinarioUuid,
        @NotNull LocalDateTime dataHora,
        @NotBlank String motivo,
        @NotNull BigDecimal valor
) {}
