package com.detailempire.vehicle.controller;

import com.detailempire.vehicle.model.VehicleRequest;
import com.detailempire.vehicle.model.VehicleResponse;
import com.detailempire.vehicle.security.UserPrincipal;
import com.detailempire.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // frontend Vite
public class VehicleController {

    private final VehicleService vehicleService;

    // Crear un vehículo del usuario autenticado
    @PostMapping
    public VehicleResponse create(@Valid @RequestBody VehicleRequest request) {
        return vehicleService.createVehicle(request);
    }

    @GetMapping("/my")
    public List<VehicleResponse> getMyVehicles() {
        return vehicleService.getMyVehicles();
    }
    @PutMapping("/{id}")
    public VehicleResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request
    ) {
        return vehicleService.updateMyVehicle(id, request);
    }

    // 👇 eliminar vehículo
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehicleService.deleteMyVehicle(id);
    }
}