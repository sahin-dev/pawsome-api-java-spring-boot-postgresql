package com.pawsome.api.service.dtos;

import com.pawsome.api.service.enums.ServiceStatus;

import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateServiceDto {


    @Size(max = 20, min = 3)
    private String title;



    private String description;



    @DecimalMin(value = "0.0")
    @Positive
    private Float price;



    @DecimalMin(value = "0.0")
    @Positive
    private Float duration;


    private ServiceStatus status;

}
