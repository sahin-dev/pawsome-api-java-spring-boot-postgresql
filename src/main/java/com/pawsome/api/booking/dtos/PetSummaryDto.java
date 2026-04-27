package com.pawsome.api.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetSummaryDto {

    private String id;

    private String petName;

    private String petImageUrl;

    private String breed;

    private Float age;

}
