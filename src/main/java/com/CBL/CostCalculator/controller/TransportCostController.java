package com.CBL.CostCalculator.controller;

import com.CBL.CostCalculator.dto.CostRequest;
import com.CBL.CostCalculator.dto.CostResponse;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.service.TransportCostService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/v1/cost")
public class TransportCostController {

    @Autowired
    private TransportCostService transportCostService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransportCost(@RequestBody CostRequest costRequest) {
        log.info("Transport Cost Controller::Creating transport cost started"+costRequest.toString());
        try{
            CostResponse costResponse = transportCostService.createTransportCost(costRequest);
            return ResponseEntity.ok(costResponse);
        }catch (GeneralBusinessException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception e) {
            log.error("Transport Cost Controller::Error while creating transport cost: {}", e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTransportCost(@PathVariable Integer id,@RequestBody CostRequest costRequest) {
        log.info("Transport Cost Controller::Updating transport cost started");
        try{
            CostResponse costResponse = transportCostService.updateTransportCost(id,costRequest);
            return ResponseEntity.ok(costResponse);
        }catch (GeneralBusinessException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception e) {
            log.error("Transport Cost Controller::Error while updating transport cost: {}", e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAllTransportCost(@PathVariable Integer id) {
        log.info("Transport Cost Controller::Getting transport cost started");
        try{
            CostResponse costResponse = transportCostService.getTransportCost(id);
            return ResponseEntity.ok(costResponse);
        }catch (GeneralBusinessException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception e) {
            log.error("Transport Cost Controller::Error while getting transport cost: {}", e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getAll")
    public List<CostResponse> getAllTransportCost() {
        log.info("Transport Cost Controller::Getting all transport cost started");
        try{
            List<CostResponse> costResponse = transportCostService.getAllTransportCost();
            return costResponse;
        }catch (GeneralBusinessException ex){
           return null;
        }
        catch (Exception e) {
            log.error("Transport Cost Controller::Error while getting all transport cost: {}", e.getMessage());
           return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransportCost(@PathVariable Integer id) {
        log.info("Transport Cost Controller::Deleting transport cost started");
        try{
            String response = transportCostService.deleteTransportCost(id);
            return ResponseEntity.ok(response);
        }catch (GeneralBusinessException ex){
           return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (Exception e) {
            log.error("Transport Cost Controller::Error while deleting transport cost: {}", e.getMessage());
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
