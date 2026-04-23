package com.pawsome.api.pet;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pawsome.api.auth.User;
import com.pawsome.api.pet.enums.PetTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Table(name = "pets")
@Entity
@Data
@Accessors(chain = true)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;
    
    @Column(name = "pet_name", nullable = false, length = 20)
    private String petName;

    @Column(name = "image_url", nullable = false)
    private String petImageUrl;

    @Column(name = "pet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetTypeEnum petType;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private Float age;

    @Column(nullable = false)
    private Float weight;

    @Column(name = "medical_notes",nullable = false)
    private String medicalNotes;    

    @Column(name = "feeding_instruction", nullable = false)
    private String feedingInstructions;

    @Column(name = "behaviour_notes", nullable = true)
    private String behaviourNotes;

    @Column(name = "special_instructions", nullable = true)
    private String specialInstructions;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
