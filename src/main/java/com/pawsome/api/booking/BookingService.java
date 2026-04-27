package com.pawsome.api.booking;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pawsome.api.auth.User;
import com.pawsome.api.booking.dtos.CreateBookingDto;
import com.pawsome.api.exception.ResourceNotFoundException;
import com.pawsome.api.pet.Pet;
import com.pawsome.api.pet.PetRepository;
import com.pawsome.api.service.PetService;
import com.pawsome.api.service.ServiceRepository;
import com.pawsome.api.service.enums.ServiceStatus;
import com.pawsome.api.service.exceptions.ServiceNotAvailableException;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;
    public BookingService(BookingRepository bookingRepository, ServiceRepository serviceRepository, PetRepository petRepository){
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
        this.petRepository = petRepository;
    }

    public Booking createBooking(User user, CreateBookingDto createBookingDto) throws Exception{

        PetService petService = this.serviceRepository.findById(createBookingDto.getService())
        .orElseThrow(() -> new ResourceNotFoundException("Service not found with id "+createBookingDto.getService()));

        if(petService.getStatus() == ServiceStatus.INACTIVE){
            throw new ServiceNotAvailableException("service not avaailable"); 
        }

        Pet pet = this.petRepository.findWithGallaries(createBookingDto.getPet()).orElseThrow(() -> new ResourceNotFoundException("Pet not found with id "+createBookingDto.getPet()));

    
        Booking booking =  modelMapper.map(createBookingDto, Booking.class);

        booking.setUser(user);
        booking.setService(petService);
        booking.setPet(pet);
        booking.setServiceEndedAt(getServiceEndTime(createBookingDto.getServiceStartedAt(), petService));
        booking.setCords(createBookingDto.getCords());

        return this.bookingRepository.save(booking);
    }

    public Page<Booking> getAllBookings(Pageable pageable){

        Page<Booking> bookings = this.bookingRepository.findAll(pageable); 
        
        return bookings;
    }

    private LocalDateTime getServiceEndTime(LocalDateTime startTime, PetService petService){
         Long serviceDuration = (long) (petService.getDuration() * 60);
        LocalDateTime endTime = startTime.plusMinutes(serviceDuration);

        return endTime;
    }

}
