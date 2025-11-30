package com.detailempire.vehicle.repository;



import com.detailempire.vehicle.model.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findByOwnerId(Long ownerId);
    Optional<VehicleEntity> findByIdAndOwnerId(Long id, Long ownerId);
}
