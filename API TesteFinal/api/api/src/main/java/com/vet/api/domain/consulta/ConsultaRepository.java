package com.vet.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("select c from Consulta c join fetch c.pet join fetch c.veterinario")
    List<Consulta> findAllComRelacionamentos();

    @Query("select c from Consulta c join fetch c.pet join fetch c.veterinario where c.uuid = :uuid")
    Optional<Consulta> findByUuid(UUID uuid);
}
