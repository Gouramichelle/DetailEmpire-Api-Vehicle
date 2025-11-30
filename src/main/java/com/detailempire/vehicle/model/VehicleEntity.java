package com.detailempire.vehicle.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dueño del vehículo (userId que viene en el token)
    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String brand; // Toyota, Hyundai, etc.

    @Column(nullable = false)
    private String model; // Yaris, Tucson, etc.

    private Integer year;

    @Column(nullable = false, unique = true)
    private String plate; // patente, ej: LKRW-94

    private String color;
}
