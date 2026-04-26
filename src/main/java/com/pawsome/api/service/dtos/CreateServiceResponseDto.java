package com.pawsome.api.service.dtos;

import com.pawsome.api.service.enums.ServiceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain =  true)
public class CreateServiceResponseDto {

    private String id;

    private String title;

    private String description;

    private Float price;

    private Float duration;

    private ServiceStatus status;

}
