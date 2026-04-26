package com.pawsome.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<PetService, String>{

    PetService save(PetService service);
    
    List<PetService> findAll ();

    Optional<PetService> findById(String id);

    void delete(PetService petService);

}
