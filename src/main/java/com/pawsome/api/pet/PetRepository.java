package com.pawsome.api.pet;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pawsome.api.auth.User;




public interface PetRepository extends JpaRepository<Pet, String> {
    
    @Override <S extends Pet> S save(S entity);

    @Override
    List<Pet> findAll();
    Page<Pet> findByOwner(User owner, Pageable pageable);

    Optional<Pet> findById(String petId);

    @Query("SELECT p FROM Pet p LEFT JOIN FETCH p.petGallaries WHERE p.id = :id")
    Optional<Pet> findWithGallaries(String id);

    void delete(Pet pet);


}
