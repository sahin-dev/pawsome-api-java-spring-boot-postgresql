package com.pawsome.api.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pawsome.api.auth.User;


public interface BookingRepository extends JpaRepository<Booking, String>{
    
    Booking save(Booking booking);

    Page<Booking> findByUser(User user, Pageable pagable);

    Page<Booking> findAll(Pageable pagable);
}
