package com.pawsome.api.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSummaryDto {

    private String title;

    private Float price;

    private Float duration;

}
