package com.vet.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosAtualizacaoConsulta(
        @NotNull LocalDateTime dataHora,
        @NotNull String motivo,
        String diagnostico,
        @NotNull BigDecimal valor,
        @NotNull StatusConsulta status
) {}
