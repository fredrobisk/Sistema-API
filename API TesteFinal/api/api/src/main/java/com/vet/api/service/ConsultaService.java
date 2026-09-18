package com.vet.api.service;

import com.vet.api.domain.consulta.Consulta;
import com.vet.api.domain.consulta.ConsultaRepository;
import com.vet.api.domain.consulta.DadosAgendamentoConsulta;
import com.vet.api.domain.consulta.DadosAtualizacaoConsulta;
import com.vet.api.domain.pet.PetRepository;
import com.vet.api.domain.veterinario.VeterinarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;

    public ConsultaService(ConsultaRepository repository,
                           PetRepository petRepository,
                           VeterinarioRepository veterinarioRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Consulta> listar() {
        return repository.findAllComRelacionamentos();
    }

    public Consulta buscarPorUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Consulta não encontrada"));
    }

    @Transactional
    public Consulta agendar(DadosAgendamentoConsulta dados) {
        if (dados.dataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A consulta não pode ser agendada no passado");
        }

        var pet = petRepository.findByUuid(dados.petUuid())
                .orElseThrow(() -> new NoSuchElementException("Pet não encontrado"));
        var veterinario = veterinarioRepository.findByUuid(dados.veterinarioUuid())
                .orElseThrow(() -> new NoSuchElementException("Veterinário não encontrado"));

        return repository.save(new Consulta(dados, pet, veterinario));
    }

    @Transactional
    public Consulta atualizar(UUID uuid, DadosAtualizacaoConsulta dados) {
        var consulta = buscarPorUuid(uuid);
        consulta.atualizar(dados);
        return consulta;
    }

    @Transactional
    public void excluir(UUID uuid) {
        var consulta = buscarPorUuid(uuid);
        repository.delete(consulta);
    }
}
