package com.CBL.CostCalculator.service;

import com.CBL.CostCalculator.dto.VehicleRequest;
import com.CBL.CostCalculator.dto.VehicleResponse;
import com.CBL.CostCalculator.dtoMapper.VehicleDtoMapper;
import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.entity.Vehicle;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.VehicleRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RegionService regionService;

    public VehicleResponse createVehicle(VehicleRequest vehicleRequest) {
        log.info("Vehicle Service::Creating vehicle started");
        try{
           if (isValidation(vehicleRequest)) {
                Vehicle vehicle = mapToEntity(vehicleRequest);
                vehicle = vehicleRepository.save(vehicle);
                log.info("Vehicle Service::Vehicle created successfully");
                return mapToResponse(vehicle);
            }
            throw new GeneralBusinessException("Vehicle creation failed");
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while creating vehicle: {}", e.getMessage());
            throw e;
        }

    }

    public VehicleResponse updateVehicle( Integer id,VehicleRequest vehicleRequest) {
        log.info("Vehicle Service::Updating vehicle started");
        try{
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Vehicle not found with id: " + id ));
            if (isUpdateValidation(vehicleRequest,vehicle)) {
                vehicle = mapToEntity(vehicleRequest);
                vehicle.setId(id);
                vehicle = vehicleRepository.save(vehicle);
                log.info("Vehicle Service::Vehicle updated successfully");
                return mapToResponse(vehicle);
            }
            throw new GeneralBusinessException("Vehicle updation failed");
        }catch (GeneralBusinessException ex){
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while updating vehicle: {}", e.getMessage());
            throw e;
        }
    }

    public VehicleResponse getVehicle(Integer id) {
        log.info("Vehicle Service::Getting vehicle started");
        try{
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Vehicle not found with id: " + id ));
            log.info("Vehicle Service::Vehicle retrieved successfully");
            return mapToResponse(vehicle);
        }catch (GeneralBusinessException ex){
            log.error("General Exception:Error while getting vehicle: "+ ex.getMessage());
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while getting vehicle: {}", e.getMessage());
            throw e;
        }
    }

    public List<VehicleResponse> getAllVehicle() {
        log.info("Vehicle Service::Getting all vehicles started");
        try{
            List<Vehicle> vehicles = vehicleRepository.findAll();
            if (vehicles.isEmpty()) {
                log.error("Vehicle Service::No vehicles found");
                throw new GeneralBusinessException("No vehicles found");
            }
            log.info("Vehicle Service::Vehicles retrieved successfully");
            return vehicles.stream().map(this::mapToResponse).toList();
        }catch (Exception e) {
            log.error("Error while getting all vehicles: {}", e.getMessage());
            throw e;
        }
    }


    public List<VehicleResponse> getVehiclesByRegionId(Integer regionId) {
        log.info("Vehicle Service::Getting vehicles by region id started");
        try{
            Region region = regionService.getRegionEntity(regionId);
            List<Vehicle> vehicles = vehicleRepository.findByRegion(region);
            if (vehicles.isEmpty()) {
                log.error("Vehicle Service::No vehicles found");
                throw new GeneralBusinessException("No vehicles found");
            }
            log.info("Vehicle Service::Vehicles retrieved successfully");
            return vehicles.stream().map(this::mapToResponse).toList();
        }catch (Exception e) {
            log.error("Error while getting vehicles by region id: {}", e.getMessage());
            throw e;
        }
    }

    public String deleteVehicle(Integer id){
        try{
            log.info("Vehicle Service::Deleting vehicle started");
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Vehicle not found with id: " + id ));
            vehicleRepository.delete(vehicle);
            return "Vehicle deleted successfully";
        }catch (GeneralBusinessException ex){
            log.error("General Exception:Error while deleting vehicle: "+ ex.getMessage());
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while deleting vehicle: {}", e.getMessage());
            throw e;
        }

    }

    // get vehicle
    public Vehicle getVehicleEntity(Integer id) {
        log.info("Vehicle Service::Getting vehicle started");
        try{
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Vehicle not found with id: " + id ));
            log.info("Vehicle Service::Vehicle retrieved successfully");
            return vehicle;
        }catch (GeneralBusinessException ex){
            log.error("General Exception:Error while getting vehicle: "+ ex.getMessage());
            throw ex;
        }
        catch (Exception e) {
            log.error("Error while getting vehicle: {}", e.getMessage());
            throw e;
        }
    }



    private Vehicle mapToEntity(VehicleRequest vehicleRequest) {
        VehicleDtoMapper vehicleDtoMapper = new VehicleDtoMapper();
        return vehicleDtoMapper.ConvertToEntity(vehicleRequest, regionService);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        VehicleDtoMapper vehicleDtoMapper = new VehicleDtoMapper();
        return vehicleDtoMapper.ConvertToResponse(vehicle);
    }

    private void requestValidation(VehicleRequest vehicleRequest){
        if(vehicleRequest.getType() == null || vehicleRequest.getType().isEmpty()){
            log.error("Vehicle Service::Vehicle type is empty");
            throw new GeneralBusinessException("Vehicle type is empty");
        }

        if(vehicleRequest.getVehicleNumber() == null || vehicleRequest.getVehicleNumber().isEmpty()){
            log.error("Vehicle Service::Vehicle number is empty");
            throw new GeneralBusinessException("Vehicle number is empty");
        }

        if(vehicleRequest.getCapacity() == null){
            log.error("Vehicle Service::Vehicle capacity is empty");
            throw new GeneralBusinessException("Vehicle capacity is empty");
        }

        if(vehicleRequest.getCostPerKM() == null){
            log.error("Vehicle Service::Vehicle cost per KM is empty");
            throw new GeneralBusinessException("Vehicle cost per KM is empty");
        }

        if(vehicleRequest.getRegionId() == null){
            log.error("Vehicle Service::Vehicle region is empty");
            throw new GeneralBusinessException("Vehicle region is empty");
        }

        if (vehicleRequest.getCapacity() < 0) {
            log.error("Vehicle Service::Vehicle capacity is negative");
            throw new GeneralBusinessException("Vehicle capacity is negative");
        }

    }

    private boolean isValidation(VehicleRequest vehicleRequest){
        requestValidation(vehicleRequest);
        if (vehicleRepository.existsByVehicleNumber(vehicleRequest.getVehicleNumber())) {
            log.error("Vehicle Service::Vehicle number already exists");
            throw new GeneralBusinessException("Vehicle number already exists");
        }
        return true;
    }

    private boolean isUpdateValidation(VehicleRequest vehicleRequest,Vehicle vehicle){
        requestValidation(vehicleRequest);
        if (vehicleRepository.existsByVehicleNumber(vehicleRequest.getVehicleNumber()) && !vehicleRequest.getVehicleNumber().equals(vehicle.getVehicleNumber())) {
            log.error("Vehicle Service::Vehicle number already exists");
            throw new GeneralBusinessException("Vehicle number already exists");
        }
        return true;
    }


}
