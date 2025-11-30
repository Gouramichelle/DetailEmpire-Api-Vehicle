package com.detailempire.vehicle.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VehicleResponse {

    private Long id;
    private Long ownerId;
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private String color;
}
