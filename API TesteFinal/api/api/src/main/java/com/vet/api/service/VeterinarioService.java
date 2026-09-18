package com.vet.api.service;

import com.vet.api.domain.veterinario.DadosAtualizacaoVeterinario;
import com.vet.api.domain.veterinario.DadosCadastroVeterinario;
import com.vet.api.domain.veterinario.Veterinario;
import com.vet.api.domain.veterinario.VeterinarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repository;

    public VeterinarioService(VeterinarioRepository repository) {
        this.repository = repository;
    }

    public List<Veterinario> listar() {
        return repository.findAll();
    }

    public Veterinario buscarPorUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Veterinário não encontrado"));
    }

    @Transactional
    public Veterinario salvar(DadosCadastroVeterinario dados) {
        return repository.save(new Veterinario(dados));
    }

    @Transactional
    public Veterinario atualizar(UUID uuid, DadosAtualizacaoVeterinario dados) {
        var veterinario = buscarPorUuid(uuid);
        veterinario.atualizar(dados);
        return veterinario;
    }

    @Transactional
    public void excluir(UUID uuid) {
        var veterinario = buscarPorUuid(uuid);
        repository.delete(veterinario);
    }
}
