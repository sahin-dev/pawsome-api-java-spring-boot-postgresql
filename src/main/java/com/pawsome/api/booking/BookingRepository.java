package com.pawsome.api.booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String>{
    
    Booking save(Booking booking);
}
