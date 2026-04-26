package com.pawsome.api.pet;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pawsome.api.auth.User;
import com.pawsome.api.auth.exception.UserNotFoundException;
import com.pawsome.api.auth.repositories.UserRepository;
import com.pawsome.api.common.PaginatedResponse;
import com.pawsome.api.exception.ResourceNotFoundException;
import com.pawsome.api.pet.dtos.AddPetImageDto;
import com.pawsome.api.pet.dtos.CreatePetDto;
import com.pawsome.api.pet.dtos.ListUserPetsDto;
import com.pawsome.api.pet.exception.FileUploadFailedException;
import com.pawsome.api.pet.exception.PetNotFoundException;


@Service
public class PetService {

    private PetRepository petRepository;
    private UserRepository userRepository;
    @Autowired
    private PetGalleryRepository petGalleryRepository;
    private Path root = Paths.get("uploads");

    public PetService(PetRepository petRepository, UserRepository userRepository){
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public Pet createPet(String ownerId, CreatePetDto createPetDto, MultipartFile petImageFile) throws IOException, UserNotFoundException{

        User owner = this.userRepository.findById(ownerId).orElseThrow(() ->  new ResourceNotFoundException("User not found with id: "+ ownerId));

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

        User user = this.userRepository.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+ownerId));

        Page<Pet> petPage = this.petRepository.findByOwner(user, pageable);
        Page<ListUserPetsDto> dtoPage = petPage.map(pet -> new ListUserPetsDto(pet.getId(),pet.getPetName(), pet.getPetImageUrl(),pet.getBreed(), pet.getAge()));

        return PaginatedResponse.of(dtoPage);
    }

    public Pet getPetDetails(String petId){

        Pet pet = this.petRepository.findWithGallaries(petId).orElseThrow(() -> new ResourceNotFoundException("Pet not found with id "+ petId));
        
        return pet;
    }

    public Pet deletePet(String petId){
        Pet pet = this.petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("pet not found with id "+petId));

        this.petRepository.delete(pet);

        return pet;
    }

    public PetGallary uploadPetImage(AddPetImageDto addPetImageDto, MultipartFile file){

        Pet pet = this.petRepository.findById(addPetImageDto.getPetId())
            .orElseThrow(() -> new PetNotFoundException("Pet not found with id "+ addPetImageDto.getPetId()));

        try{
            Path galleryPath = root.resolve("gallery");

            if(!Files.exists(galleryPath)){
                Files.createDirectories(galleryPath);
            }

            Path filePath = galleryPath.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            PetGallary petGallary = new PetGallary()
                .setPet(pet)
                .setImageUrl(filePath.toString())
                .setOrder(Integer.parseInt(addPetImageDto.getOrder()));

            Optional<PetGallary> existingPetGallary = this.petGalleryRepository.findByPetIdAndOrder(addPetImageDto.getPetId(), Integer.parseInt(addPetImageDto.getOrder()));

            if(existingPetGallary.isPresent()){
                return this.replaceExistingImage(existingPetGallary.get().getId(), filePath.toString());
            }

            return this.petGalleryRepository.save(petGallary);

        }catch(IOException ex){
            ex.printStackTrace();
            throw new FileUploadFailedException(ex.getMessage());
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    @Transactional
    public PetGallary replaceExistingImage(String galleryId, String url){

        PetGallary petGallary = this.petGalleryRepository.findById(galleryId).orElseThrow(() -> new RuntimeException("Gallery not found with id "+ galleryId));

       petGallary.setImageUrl(url);
       
       return  this.petGalleryRepository.save(petGallary);
    }

    public List<PetGallary> getAllPetImages(String petId){

        Pet pet = this.petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found with id: "+ petId));

        List<PetGallary> petGallaries = this.petGalleryRepository.findByPet(pet);

        return petGallaries;
    }

}
