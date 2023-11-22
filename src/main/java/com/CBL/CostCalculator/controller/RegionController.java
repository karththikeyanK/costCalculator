package com.CBL.CostCalculator.controller;

import com.CBL.CostCalculator.dto.RegionRequest;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<?> createRegion(@RequestBody RegionRequest regionRequest){
        try{
            return ResponseEntity.ok(regionService.createRegion(regionRequest));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while creating region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while creating region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRegion(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(regionService.getRegion(id));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRegion(){
        try{
            return ResponseEntity.ok(regionService.getAllRegion());
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting all region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting all region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable Integer id, @RequestBody RegionRequest regionRequest){
        try{
            return ResponseEntity.ok(regionService.updateRegion(id, regionRequest));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while updating region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while updating region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(regionService.deleteRegion(id));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while deleting region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while deleting region: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
