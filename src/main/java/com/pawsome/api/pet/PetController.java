package com.pawsome.api.pet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pawsome.api.auth.User;
import com.pawsome.api.auth.exception.UserNotFoundException;
import com.pawsome.api.common.PaginatedResponse;
import com.pawsome.api.pet.dtos.AddPetImageDto;
import com.pawsome.api.pet.dtos.CreatePetDto;
import com.pawsome.api.pet.dtos.ListUserPetsDto;
import com.pawsome.api.response.ApiResponse;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Pet> createPet( @AuthenticationPrincipal User authenticatedUser,
         @ModelAttribute @Valid CreatePetDto createPetDto, 
         @RequestParam("image") MultipartFile file) throws IOException, UserNotFoundException{

        Pet pet = this.petService.createPet(authenticatedUser.getId(), createPetDto, file);
        
        return new ApiResponse<>(
            true,
            pet,
            "Pet created successfully"
        );
    }
    @GetMapping("/users/{userId}")
    public ApiResponse<?> getAllUserPets(@PathVariable String userId,
         @RequestParam(name = "page", defaultValue = "0") int page,
         @RequestParam(name = "limit", defaultValue = "10") int limit
        ) throws UserNotFoundException{

        Pageable pageable = PageRequest.of(page, limit);
        
        PaginatedResponse<ListUserPetsDto> response = this.petService.getAllUserPets(userId, pageable);

      

        return new ApiResponse<>(
            true,
            response,
            "Pets fetched successfully"
        );
    }

    @GetMapping("{petId}")
    public ApiResponse<?> getPetDetails(@PathVariable String petId){

        Pet pet = this.petService.getPetDetails(petId);

        return new ApiResponse<>(
            true,
            pet,
            "Pet details fetched successfully"
        );
    }

    @PostMapping(path = "/gallery/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> addPetImage(@ModelAttribute @RequestBody @Valid AddPetImageDto addPetImageDto, @RequestParam("image") MultipartFile file){

        PetGallary petGallary = this.petService.uploadPetImage(addPetImageDto, file);

        return new ApiResponse<>(
            true,
            petGallary,
            "Pet Image uploaded successfully"
        );
    }

    @GetMapping("/{petId}/images")
    public ApiResponse<?> getAllPetImages(@PathVariable String petId){

        

        List<PetGallary> petGallaries = this.petService.getAllPetImages(petId);

        return new ApiResponse<>(
            true,
            petGallaries,
            "Pet images fetched successfully"
        );

    }

    @DeleteMapping("{petId}")
    public ApiResponse<?> deletePet(@PathVariable String petId){

        Pet pet = this.petService.deletePet(petId);

        return new ApiResponse<>(true, pet, "pet deleted successsfully");
    }
    

}
