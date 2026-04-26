package com.pawsome.api.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pawsome.api.exception.ResourceNotFoundException;
import com.pawsome.api.service.dtos.CreateServiceDto;
import com.pawsome.api.service.dtos.UpdateServiceDto;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ServiceService(ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    public PetService createService(CreateServiceDto createServiceDto){

        PetService service = this.modelMapper.map(createServiceDto, PetService.class);
        
        return this.serviceRepository.save(service);
    }

    public List<PetService> getAllPetService(){

        List<PetService> petServices = this.serviceRepository.findAll();

        return petServices;
    }

    public PetService getServiceDetails(String id){

        PetService petService = this.serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found with id "+id));

        return petService;
    }

    public void deletePetService(String id){

        PetService petService = this.serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found with id "+ id));

        this.serviceRepository.delete(petService);
    }
public PetService updateService(String id, UpdateServiceDto dto) {

    PetService petService = this.serviceRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Service not found with id " + id));

    if (dto.getTitle() != null) {
        petService.setTitle(dto.getTitle());
    }

    if (dto.getPrice() != null) {
        petService.setPrice(dto.getPrice());
    }

    if (dto.getDuration() != null) {
        petService.setDuration(dto.getDuration());
    }

    if (dto.getStatus() != null) {
        petService.setStatus(dto.getStatus());
    }

    return this.serviceRepository.save(petService);
}

}
