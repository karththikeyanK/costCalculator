package com.CBL.CostCalculator.controller;

import com.CBL.CostCalculator.dto.VehicleRequest;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/create")
    public ResponseEntity<?> createVehicle(@RequestBody VehicleRequest vehicleRequest){
        try{
            return ResponseEntity.ok(vehicleService.createVehicle(vehicleRequest));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while creating vehicle: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while creating vehicle: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@PathVariable Integer vehicleId,@RequestBody VehicleRequest vehicleRequest){
        try{
            return ResponseEntity.ok(vehicleService.updateVehicle(vehicleId,vehicleRequest));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while updating vehicle: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while updating vehicle: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get/{vehicleId}")
    public ResponseEntity<?> getVehicle(@PathVariable Integer vehicleId){
        try{
            return ResponseEntity.ok(vehicleService.getVehicle(vehicleId));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting vehicle: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting vehicle: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllVehicles(){
        try{
            return ResponseEntity.ok(vehicleService.getAllVehicle());
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting all vehicles: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting all vehicles: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllByRegion/{regionId}")
    public ResponseEntity<?> getAllVehiclesByRegion(@PathVariable Integer regionId){
        try{
            return ResponseEntity.ok(vehicleService.getVehiclesByRegionId(regionId));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting all vehicles: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting all vehicles: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Integer vehicleId){
        try{
            return ResponseEntity.ok(vehicleService.deleteVehicle(vehicleId));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while deleting vehicle: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while deleting vehicle: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
