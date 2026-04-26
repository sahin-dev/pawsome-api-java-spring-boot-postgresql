package com.pawsome.api.service;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.pawsome.api.service.enums.ServiceStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "services")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PetService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(nullable = false,length = 20)
    private String title;


    @Column(nullable = true)
    private String description;
    

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    private Float price;


    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    private Float duration;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceStatus status = ServiceStatus.INACTIVE;


    @Column(nullable = false)
    private String icon = "icon";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
}
