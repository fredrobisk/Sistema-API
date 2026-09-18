package com.vet.api.domain.consulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DadosDetalhamentoConsulta(
        UUID uuid,
        LocalDateTime dataHora,
        String motivo,
        String diagnostico,
        BigDecimal valor,
        StatusConsulta status,
        UUID petUuid,
        String nomePet,
        UUID veterinarioUuid,
        String nomeVeterinario
) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(
                consulta.getUuid(),
                consulta.getDataHora(),
                consulta.getMotivo(),
                consulta.getDiagnostico(),
                consulta.getValor(),
                consulta.getStatus(),
                consulta.getPet().getUuid(),
                consulta.getPet().getNome(),
                consulta.getVeterinario().getUuid(),
                consulta.getVeterinario().getNome()
        );
    }
}
