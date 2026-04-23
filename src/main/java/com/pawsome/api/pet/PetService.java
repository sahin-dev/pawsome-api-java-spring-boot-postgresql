package com.pawsome.api.pet;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pawsome.api.auth.User;
import com.pawsome.api.auth.exception.UserNotFoundException;
import com.pawsome.api.auth.repositories.UserRepository;
import com.pawsome.api.common.PaginatedResponse;
import com.pawsome.api.pet.dtos.CreatePetDto;
import com.pawsome.api.pet.dtos.ListUserPetsDto;
import com.pawsome.api.pet.exception.FileUploadFailedException;
import com.pawsome.api.pet.exception.PetNotFoundException;

@Service()
public class PetService {

    private PetRepository petRepository;
    private UserRepository userRepository;
    private Path root = Paths.get("uploads");

    public PetService(PetRepository petRepository, UserRepository userRepository){
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public Pet createPet(String ownerId, CreatePetDto createPetDto, MultipartFile petImageFile) throws IOException, UserNotFoundException{

        User owner = this.userRepository.findById(ownerId).orElseThrow(() ->  new UserNotFoundException("User not found with id: "+ ownerId));

        try{
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }

            Files.copy(petImageFile.getInputStream(),this.root.resolve(petImageFile.getOriginalFilename()));

        }catch(Exception ex){
            
            throw new FileUploadFailedException("File upload Failed");
        }

        
        Pet pet = createPetDto.dtoToModel(createPetDto);
        pet.setPetImageUrl("/uploads/"+ petImageFile.getOriginalFilename());
        pet.setOwner(owner);
        return petRepository.save(pet);
    }

    public PaginatedResponse<ListUserPetsDto> getAllUserPets(String ownerId, Pageable pageable) throws UserNotFoundException{

        User user = this.userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException("User not found with id "+ownerId));

        Page<Pet> petPage = this.petRepository.findByOwner(user, pageable);
        Page<ListUserPetsDto> dtoPage = petPage.map(pet -> new ListUserPetsDto(pet.getId(),pet.getPetName(), pet.getPetImageUrl(),pet.getBreed(), pet.getAge()));

        return PaginatedResponse.of(dtoPage);
    }

    public Pet getPetDetails(String petId){

        Pet pet = this.petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found with id "+ petId));
    
        return pet;
    }


    public PetGallary uploadPetImage(String petId, MultipartFile file)throws IOException{

        Pet pet = this.petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found with id "+petId));

        try{
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }

            Files.copy(file.getInputStream(), this.root.resolve("/gallery"+file.getOriginalFilename()));
        }catch(Exception ex){
            throw new FileUploadFailedException(ex.getMessage());
        }
        

    }

}
