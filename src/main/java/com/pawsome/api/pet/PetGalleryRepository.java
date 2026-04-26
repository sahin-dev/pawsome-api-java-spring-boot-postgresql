package com.pawsome.api.pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PetGalleryRepository extends JpaRepository<PetGallary,String>{

    @Override
    PetGallary save(PetGallary entity);
    
    @Modifying
    @Query("UPDATE pet_gallary p SET p.imageUrl = :url WHERE p.id = :id")
    int updateImageUrl(@Param("id")String id, @Param("image_url") String url);
    Optional<PetGallary> findById(String id);

    Optional<PetGallary> findByPetIdAndOrder(String petId, Integer order);

    List<PetGallary> findByPet(Pet pet);
    
}
