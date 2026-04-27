package com.pawsome.api.booking;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pawsome.api.auth.User;
import com.pawsome.api.booking.dtos.BookingResponseDto;
import com.pawsome.api.booking.dtos.CreateBookingDto;
import com.pawsome.api.booking.dtos.PetSummaryDto;
import com.pawsome.api.common.PaginatedResponse;
import com.pawsome.api.pet.Pet;
import com.pawsome.api.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;
    @Autowired
    private ModelMapper mapper;

    public BookingController(BookingService bookingService){

        this.bookingService = bookingService;
    }

    @PostMapping
    public ApiResponse<?> createBooking(@RequestBody @Valid CreateBookingDto bookingDto, @AuthenticationPrincipal User user) throws Exception{

        Booking createdBooking = this.bookingService.createBooking(user, bookingDto);

        TypeMap<Booking, BookingResponseDto> bookingResponseMapper = this.mapper.typeMap(Booking.class, BookingResponseDto.class)
        .addMappings(mapper -> mapper.map(Booking::getServiceStartedAt, BookingResponseDto::setService_started_at))
        .addMappings(mapper -> mapper.map(Booking::getServiceEndedAt, BookingResponseDto::setService_ended_at));



        BookingResponseDto bookingResponseDto =  bookingResponseMapper.map(createdBooking);

        return new ApiResponse<>(true, bookingResponseDto, "booking created successfully");
    }
    
    @GetMapping
    public ApiResponse<?> getAllBookings(@RequestParam(name = "page", defaultValue = "0") String page, 
    @RequestParam(name = "limit", defaultValue = "20") String limit){

        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit));
        Page<Booking> bookings = this.bookingService.getAllBookings(pageable);

           List<BookingResponseDto> dtoList = bookings.getContent()
            .stream()
            .map(b -> mapper.map(b, BookingResponseDto.class))
            .toList();

    Page<BookingResponseDto> dtoPage =
        new PageImpl<>(dtoList, bookings.getPageable(), bookings.getTotalElements());

        PaginatedResponse<BookingResponseDto> response = PaginatedResponse.of(dtoPage);

        return new ApiResponse<>(
            true,
            response,
            "Bookings fetched successfully"
        );

    }

   

}
