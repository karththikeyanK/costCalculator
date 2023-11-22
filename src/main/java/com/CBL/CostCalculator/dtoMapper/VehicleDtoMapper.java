package com.CBL.CostCalculator.dtoMapper;

import com.CBL.CostCalculator.dto.VehicleRequest;
import com.CBL.CostCalculator.dto.VehicleResponse;
import com.CBL.CostCalculator.entity.Vehicle;
import com.CBL.CostCalculator.service.RegionService;

public class VehicleDtoMapper {

    public Vehicle ConvertToEntity(VehicleRequest vehicleRequest, RegionService regionService) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(vehicleRequest.getType());
        vehicle.setVehicleNumber(vehicleRequest.getVehicleNumber());
        vehicle.setCapacity(vehicleRequest.getCapacity());
        vehicle.setCostPerKM(vehicleRequest.getCostPerKM());
        vehicle.setRegion(regionService.getRegionEntity(vehicleRequest.getRegionId()));
        return vehicle;
    }

    public VehicleResponse ConvertToResponse(Vehicle vehicle) {
       VehicleResponse vehicleResponse = new VehicleResponse();
         vehicleResponse.setId(vehicle.getId());
         vehicleResponse.setType(vehicle.getType());
         vehicleResponse.setVehicleNumber(vehicle.getVehicleNumber());
         vehicleResponse.setCapacity(vehicle.getCapacity());
         vehicleResponse.setCostPerKM(vehicle.getCostPerKM());
         vehicleResponse.setRegionId(vehicle.getRegion().getId());
         return vehicleResponse;
    }
}
