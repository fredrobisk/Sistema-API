package com.vet.api.service;

import com.vet.api.domain.cliente.ClienteRepository;
import com.vet.api.domain.pet.DadosAtualizacaoPet;
import com.vet.api.domain.pet.DadosCadastroPet;
import com.vet.api.domain.pet.Pet;
import com.vet.api.domain.pet.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PetService {

    private final PetRepository repository;
    private final ClienteRepository clienteRepository;

    public PetService(PetRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pet> listar() {
        return repository.findAllComTutor();
    }

    public Pet buscarPorUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Pet não encontrado"));
    }

    @Transactional
    public Pet salvar(DadosCadastroPet dados) {
        var cliente = clienteRepository.findByUuid(dados.clienteUuid())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));
        return repository.save(new Pet(dados, cliente));
    }

    @Transactional
    public Pet atualizar(UUID uuid, DadosAtualizacaoPet dados) {
        var pet = buscarPorUuid(uuid);
        pet.atualizar(dados);
        return pet;
    }

    @Transactional
    public void excluir(UUID uuid) {
        var pet = buscarPorUuid(uuid);
        repository.delete(pet);
    }
}
