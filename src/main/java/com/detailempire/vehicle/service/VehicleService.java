package com.detailempire.vehicle.service;

import com.detailempire.vehicle.model.VehicleEntity;
import com.detailempire.vehicle.model.VehicleRequest;
import com.detailempire.vehicle.model.VehicleResponse;
import com.detailempire.vehicle.repository.VehicleRepository;
import com.detailempire.vehicle.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    /**
     * Obtiene el id del usuario autenticado desde el SecurityContext.
     */
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new AccessDeniedException("Usuario no autenticado");
        }

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        // 👇 IMPORTANTE: usa el método correcto según tu UserPrincipal
        // Si tu clase se llama distinto (getId() en vez de getUserId()), cambia esta línea:
        Long userId = principal.getUserId();
        // Long userId = principal.getId();  // <-- usa esta si tu UserPrincipal tiene getId()

        if (userId == null) {
            throw new AccessDeniedException("El token no contiene el id de usuario.");
        }
        return userId;
    }

    /**
     * Crea un vehículo asociado al usuario actualmente autenticado.
     */
    public VehicleResponse createVehicle(VehicleRequest request) {
        Long ownerId = getCurrentUserId(); // se toma SIEMPRE del token

        VehicleEntity entity = VehicleEntity.builder()
                .ownerId(ownerId)
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .plate(request.getPlate())
                .color(request.getColor())
                .build();

        VehicleEntity saved = vehicleRepository.save(entity);
        return toResponse(saved);
    }

    /**
     * Lista los vehículos del usuario autenticado.
     */
    public List<VehicleResponse> getMyVehicles() {
        Long ownerId = getCurrentUserId();

        return vehicleRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Si luego quieres updateMyVehicle / deleteMyVehicle, se pueden agregar aquí también.

    /**
     * Convierte la entidad a DTO de respuesta.
     * Ajusta los campos según tu VehicleResponse real.
     */
    private VehicleResponse toResponse(VehicleEntity entity) {
        VehicleResponse res = new VehicleResponse();
        res.setId(entity.getId());
        res.setBrand(entity.getBrand());
        res.setModel(entity.getModel());
        res.setYear(entity.getYear());
        res.setPlate(entity.getPlate());
        res.setColor(entity.getColor());
        // Si tu VehicleResponse tiene ownerId u otros campos, los agregas:
        // res.setOwnerId(entity.getOwnerId());
        return res;
    }
    /**
     * Actualiza un vehículo del usuario autenticado.
     */
    public VehicleResponse updateMyVehicle(Long id, VehicleRequest request) {
        Long ownerId = getCurrentUserId();

        VehicleEntity entity = vehicleRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new AccessDeniedException("No puedes modificar este vehículo."));

        entity.setBrand(request.getBrand());
        entity.setModel(request.getModel());
        entity.setYear(request.getYear());
        entity.setPlate(request.getPlate());
        entity.setColor(request.getColor());

        VehicleEntity saved = vehicleRepository.save(entity);
        return toResponse(saved);
    }

    /**
     * Elimina un vehículo del usuario autenticado.
     */
    public void deleteMyVehicle(Long id) {
        Long ownerId = getCurrentUserId();

        VehicleEntity entity = vehicleRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new AccessDeniedException("No puedes eliminar este vehículo."));

        vehicleRepository.delete(entity);
    }

}
