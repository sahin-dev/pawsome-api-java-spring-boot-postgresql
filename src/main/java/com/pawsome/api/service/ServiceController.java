package com.pawsome.api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pawsome.api.response.ApiResponse;
import com.pawsome.api.service.dtos.CreateServiceDto;
import com.pawsome.api.service.dtos.CreateServiceResponseDto;
import com.pawsome.api.service.dtos.UpdateServiceDto;

@RestController
@RequestMapping("services")
public class ServiceController {

    private final  ServiceService serviceService;
    @Autowired
    private ModelMapper modelMapper;

    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }
    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<CreateServiceResponseDto> createService(@ModelAttribute CreateServiceDto createServiceDto){

        PetService createdService = this.serviceService.createService(createServiceDto);

        return new ApiResponse<>(
            true,
            this.modelMapper.map(createdService, CreateServiceResponseDto.class),
            "Service created successfully"
        );

    }
    

    @GetMapping()
    public ApiResponse<?>  getAllServices(){

        List<PetService> petServices = this.serviceService.getAllPetService();

        return new ApiResponse<>(
            true,
            petServices,
            "services fetched successfully"
        );
    }

    
    @GetMapping("{serviceId}")
    public ApiResponse<?> getServiceDetails(@PathVariable String serviceId){
    
        PetService service = this.serviceService.getServiceDetails(serviceId);

        return new ApiResponse<>(
            true,
            service,
            "service details fetched successfully"
        );
    }


    @DeleteMapping("{serviceId}")
    public ApiResponse<?> deleteService(@PathVariable String serviceId){
        this.serviceService.deletePetService(serviceId);

        return new ApiResponse<>(true, null, "Service deleted successfully");
    }

    @PatchMapping("{serviceId}")
    public ApiResponse<?> updateService(@PathVariable String serviceId, @RequestBody UpdateServiceDto updateServiceDto){
        PetService petService = this.serviceService.updateService(serviceId, updateServiceDto);

        return new ApiResponse<>(true, petService, "service updated successfully");
        
    }


}
