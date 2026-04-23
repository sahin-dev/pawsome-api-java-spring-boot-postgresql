package com.pawsome.api.pet.dtos;



public record ListUserPetsDto(
    String id,
    String petName,
    String pet_image_url,
    String breed,

    Float age)
    {
    }


    
