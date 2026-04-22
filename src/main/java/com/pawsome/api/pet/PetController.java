package com.pawsome.api.pet;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pawsome.api.pet.dtos.CreatePetDto;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPet(@ModelAttribute @Valid CreatePetDto createPetDto, @RequestParam("image") MultipartFile file) throws IOException{
        System.out.println(createPetDto);
        Pet pet = this.petService.createPet(createPetDto, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

}
