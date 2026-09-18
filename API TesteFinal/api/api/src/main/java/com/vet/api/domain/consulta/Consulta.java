package com.vet.api.domain.consulta;

import com.vet.api.domain.pet.Pet;
import com.vet.api.domain.veterinario.Veterinario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "consultas")
@Entity(name = "Consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String motivo;

    @Column(columnDefinition = "text")
    private String diagnostico;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConsulta status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    public Consulta() {
    }

    public Consulta(DadosAgendamentoConsulta dados, Pet pet, Veterinario veterinario) {
        this.dataHora = dados.dataHora();
        this.motivo = dados.motivo();
        this.valor = dados.valor();
        this.status = StatusConsulta.AGENDADA;
        this.pet = pet;
        this.veterinario = veterinario;
    }

    public void atualizar(DadosAtualizacaoConsulta dados) {
        this.dataHora = dados.dataHora();
        this.motivo = dados.motivo();
        this.diagnostico = dados.diagnostico();
        this.valor = dados.valor();
        this.status = dados.status();
    }

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public Pet getPet() {
        return pet;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consulta consulta)) return false;
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
