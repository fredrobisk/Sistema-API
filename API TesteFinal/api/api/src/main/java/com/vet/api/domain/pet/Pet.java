package com.vet.api.domain.pet;

import com.vet.api.domain.cliente.Cliente;
import com.vet.api.domain.consulta.Consulta;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "pets")
@Entity(name = "Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(nullable = false, length = 80)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Especie especie;

    @Column(length = 80)
    private String raca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pet")
    private List<Consulta> consultas = new ArrayList<>();

    public Pet() {
    }

    public Pet(DadosCadastroPet dados, Cliente cliente) {
        this.nome = dados.nome();
        this.especie = dados.especie();
        this.raca = dados.raca();
        this.cliente = cliente;
    }

    public void atualizar(DadosAtualizacaoPet dados) {
        this.nome = dados.nome();
        this.especie = dados.especie();
        this.raca = dados.raca();
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

    public String getNome() {
        return nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
