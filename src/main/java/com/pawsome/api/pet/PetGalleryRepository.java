package com.pawsome.api.pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetGalleryRepository extends JpaRepository<PetGallary,String>{

    @Override
    PetGallary save(S entity);
    

}
