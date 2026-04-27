package com.pawsome.api.booking.dtos;

import java.time.LocalDateTime;

import com.pawsome.api.booking.classes.LocationCords;
import com.pawsome.api.booking.enums.BookingStatusEnum;
import com.pawsome.api.pet.PetService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingResponseDto {

    private String id;

   private BookingStatusEnum status;

   private LocalDateTime service_started_at;

   private LocalDateTime  service_ended_at;

   private String address;
   
   private LocationCords cords;

   private PetSummaryDto pet;

   private ServiceSummaryDto service;

   private SitterSummaryDto sitter;

}
