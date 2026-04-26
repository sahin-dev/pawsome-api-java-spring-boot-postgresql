package com.pawsome.api.service.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateServiceDto {

    @NotNull
    @NotBlank
    @Size(max = 20, min = 3)
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    @DecimalMin(value = "0.0")
    private float price;

    @NotNull
    @NotBlank
    @DecimalMin(value = "0.0")
    private float duration;


}
