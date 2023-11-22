package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    boolean existsByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByRegion(Region region);
}
