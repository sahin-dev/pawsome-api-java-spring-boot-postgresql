package com.pawsome.api.pet;



import org.springframework.data.repository.CrudRepository;



public interface PetRepository extends CrudRepository<Pet, String> {
    
    @Override <S extends Pet> S save(S entity);
}
