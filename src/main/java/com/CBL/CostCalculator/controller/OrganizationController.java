package com.CBL.CostCalculator.controller;


import com.CBL.CostCalculator.dto.OrganizationRequest;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationRequest organizationRequest){
        try{
            return ResponseEntity.ok(organizationService.createOrganization(organizationRequest));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while creating organization: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while creating organization: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOrganization(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(organizationService.getOrganization(id));
        }catch (GeneralBusinessException e){
            log.error("General Exception:Error while getting organization: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            log.error("Unexpected error while getting organization: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
