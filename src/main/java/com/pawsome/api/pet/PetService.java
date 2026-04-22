package com.pawsome.api.pet;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pawsome.api.pet.dtos.CreatePetDto;

@Service()
public class PetService {

    private PetRepository petRepository;
    private Path root = Paths.get("uploads");

    public PetService(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    public Pet createPet(CreatePetDto createPetDto, MultipartFile petImageFile) throws IOException{

        if(!Files.exists(root)){
            Files.createDirectories(root);
        }

        Files.copy(petImageFile.getInputStream(),this.root.resolve(petImageFile.getOriginalFilename()));
        
        
        Pet pet = createPetDto.dtoToModel(createPetDto);
        pet.setPetImageUrl("/uploads/"+ petImageFile.getOriginalFilename());

        return petRepository.save(pet);
    }
}
