package com.vet.api.domain.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p join fetch p.cliente")
    List<Pet> findAllComTutor();

    @Query("select p from Pet p join fetch p.cliente where p.uuid = :uuid")
    Optional<Pet> findByUuid(UUID uuid);
}
