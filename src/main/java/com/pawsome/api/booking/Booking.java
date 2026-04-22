package com.pawsome.api.booking;

import java.sql.Date;

import com.pawsome.api.booking.enums.BookingStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Table(name = "bookings")
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String userId;


    @Column(nullable = false)
    private  BookingStatusEnum status = BookingStatusEnum.In_Progress;

    @Column(nullable = false, name = "service_id")
    private String serviceId;

    @Column(nullable = false, name = "pet_id")
    private String petId;

    @Column(nullable = false, name = "service_started_at")
    private Date serviceStaretdAt;


}
