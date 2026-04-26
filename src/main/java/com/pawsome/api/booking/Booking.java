package com.pawsome.api.booking;



import java.time.LocalDateTime;

import com.pawsome.api.auth.User;
import com.pawsome.api.booking.enums.BookingStatusEnum;
import com.pawsome.api.pet.Pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;




import com.pawsome.api.service.PetService;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "bookings")
@Data
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatusEnum status = BookingStatusEnum.In_Progress;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private PetService service;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(name = "service_started_at", nullable = false)
    private LocalDateTime serviceStartedAt;
}