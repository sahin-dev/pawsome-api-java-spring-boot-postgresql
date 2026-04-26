package com.pawsome.api.booking;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawsome.api.auth.User;
import com.pawsome.api.booking.dtos.CreateBookingDto;
import com.pawsome.api.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){

        this.bookingService = bookingService;
    }

    @PostMapping
    public ApiResponse<?> createBooking(@RequestBody @Valid CreateBookingDto bookingDto, @AuthenticationPrincipal User user) throws Exception{
        
        Booking createdBooking = this.bookingService.createBooking(user, bookingDto);

        return new ApiResponse<>(true, createdBooking, "booking created successfully");
    }
    

   

}
