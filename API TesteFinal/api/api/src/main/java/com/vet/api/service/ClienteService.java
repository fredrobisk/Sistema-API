package com.vet.api.service;

import com.vet.api.domain.cliente.Cliente;
import com.vet.api.domain.cliente.ClienteRepository;
import com.vet.api.domain.cliente.DadosAtualizacaoCliente;
import com.vet.api.domain.cliente.DadosCadastroCliente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscarPorUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente salvar(DadosCadastroCliente dados) {
        return repository.save(new Cliente(dados));
    }

    @Transactional
    public Cliente atualizar(UUID uuid, DadosAtualizacaoCliente dados) {
        var cliente = buscarPorUuid(uuid);
        cliente.atualizar(dados);
        return cliente;
    }

    @Transactional
    public void excluir(UUID uuid) {
        var cliente = buscarPorUuid(uuid);
        repository.delete(cliente);
    }
}
