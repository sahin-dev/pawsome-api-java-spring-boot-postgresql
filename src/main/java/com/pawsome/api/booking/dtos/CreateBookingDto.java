package com.pawsome.api.booking.dtos;


import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.validator.constraints.UUID;

import com.pawsome.api.booking.classes.LocationCords;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateBookingDto {
    
    @NotNull
    @NotBlank
    @UUID
    private String service;

    @NotNull
    @NotBlank
    @UUID
    private String pet;

    @NotBlank
    @NotNull
    private String address;
    @Valid
    private LocationCords cords;

    @FutureOrPresent
    private LocalDateTime serviceStartedAt;


}
