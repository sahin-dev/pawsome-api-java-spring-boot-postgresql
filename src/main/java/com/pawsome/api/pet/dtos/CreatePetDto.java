package com.pawsome.api.pet.dtos;

import com.pawsome.api.pet.Pet;
import com.pawsome.api.pet.enums.PetTypeEnum;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetDto {

    @NotNull(message = "Pet name cannot be null")
    @NotBlank(message = "Pet name cannot be blank")
    private String petName;

    @NotNull(message = "Pet type cannot be null")
    private PetTypeEnum petType;

    @NotNull(message = "Breed cannot be null")
    @NotBlank(message = "Breed cannot be blank")
    private String breed;

    @NotNull(message = "Age cannot be null")
    @DecimalMin(value = "0.0", message = "Age must be greater than or equal to 0")
    private Float age; 

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0.0", message = "Weight must be greater than or equal to 0")
    private Float weight;

    @NotNull(message = "Medical notes cannot be null")
    @NotBlank(message = "Medical notes cannot be blank")
    private String medicalNotes;

    @NotNull(message = "Feeding instructions cannot be null")
    @NotBlank(message = "Feeding instructions cannot be blank")
    private String feedingInstructions;


    public Pet dtoToModel(CreatePetDto createPetDto){
        return new Pet()
            .setPetName(createPetDto.getPetName())
            .setPetType(createPetDto.getPetType())
            .setBreed(createPetDto.getBreed())
            .setAge(createPetDto.getAge())
            .setWeight(createPetDto.getWeight())
            .setMedicalNotes(createPetDto.getMedicalNotes())
            .setFeedingInstructions(createPetDto.getFeedingInstructions());
    }
}
