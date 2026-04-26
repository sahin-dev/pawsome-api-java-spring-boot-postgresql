package com.pawsome.api.pet.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPetImageDto {

    @NotNull
    @NotBlank
    private String petId;

    @NotNull
    @NotBlank
    @Max(3)
    @Min(1)
    private String order;  
    
}
