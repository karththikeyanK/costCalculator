package com.CBL.CostCalculator.service;

import com.CBL.CostCalculator.dto.RegionRequest;
import com.CBL.CostCalculator.dto.RegionResponse;
import com.CBL.CostCalculator.dtoMapper.RegionDtoMapper;
import com.CBL.CostCalculator.entity.Organization;
import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private OrganizationService organizationService;

    public RegionResponse createRegion(RegionRequest regionRequest) {
        log.info("Region Service: Creating region started");
        try{

            if (isValidation(regionRequest)){
                Region region = regionRepository.save(mapToEntity(regionRequest));
                log.info("Region Service: Region object created");
                RegionResponse regionResponse = mapToResponse(region);
                log.info("Region Service: Creating region completed");
                return regionResponse;
            }
            throw new GeneralBusinessException("Region creation failed");
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while creating region: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while creating region: "+ ex.getMessage());
            throw ex;
        }
    }

    public RegionResponse getRegion(Integer id){
        log.info("Region Service: Getting region started");
        try{
            Region region = regionRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Region with id " + id + " does not exist"));
            log.info("Region Service: Region object retrieved");
            RegionResponse regionResponse = mapToResponse(region);
            log.info("Region Service: Getting region completed");
            return regionResponse;
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while getting region: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while getting region: "+ ex.getMessage());
            throw ex;
        }
    }

    public List<RegionResponse> getAllRegion() {
        log.info("Region Service: Getting all regions started");
        try{
            List<Region> region = regionRepository.findAll();
            log.info("Region Service: Region object retrieved");
            List<RegionResponse> regionResponse = region.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
            log.info("Region Service: Getting all regions completed");
            return regionResponse;
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while getting all regions: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while getting all regions: "+ ex.getMessage());
            throw ex;
        }

    }

    public RegionResponse updateRegion(Integer id,RegionRequest regionRequest){
        try{
            Region region = regionRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Region with id " + id + " does not exist"));
            if(isUpdateValidation(regionRequest,region)){
                log.info("Region Service: Region object retrieved");
                region.setName(regionRequest.getName());
                region.setCode(regionRequest.getCode());
                Organization org = organizationService.getOrganizationEntity(regionRequest.getOrgId());
                region.setOrganization(org);
                regionRepository.save(region);
                log.info("Region Service: Region object updated");
                RegionResponse regionResponse = mapToResponse(region);
                log.info("Region Service: Updating region completed");
                return regionResponse;
            }
            throw new GeneralBusinessException("Region update failed");
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while updating region: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while updating region: "+ ex.getMessage());
            throw ex;
        }
    }

    public String deleteRegion(Integer id){
        try{
            Region region = regionRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Region with id " + id + " does not exist"));
            log.info("Region Service: Region object retrieved");
            regionRepository.delete(region);
            log.info("Region Service: Region object deleted");
            return "Region deleted successfully";
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while deleting region: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while deleting region: "+ ex.getMessage());
            throw ex;
        }
    }

    public Region getRegionEntity(Integer id){
        try{
            Region region = regionRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Region with id " + id + " does not exist"));
            log.info("Region Service: Region object retrieved");
            return region;
        }catch (GeneralBusinessException ex){
            log.error("Region Service: Error while getting region: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Region Service: Unexpected error while getting region: "+ ex.getMessage());
            throw ex;
        }
    }


    public boolean isValidation(RegionRequest regionRequest){
        if (regionRequest.getName() == null || regionRequest.getName().isEmpty()){
            log.error("Region Service: Region name is empty");
            throw new GeneralBusinessException("Region name is empty");
        }
        if (regionRequest.getCode() == null || regionRequest.getCode().isEmpty()){
            log.error("Region Service: Region code is empty");
            throw new GeneralBusinessException("Region code is empty");
        }

        if (regionRepository.existsByName(regionRequest.getName()) ){
            log.error("Region Service: Region name already exists");
            throw new GeneralBusinessException("Region name already exists");
        }

        if (regionRepository.existsByCode(regionRequest.getCode())){
            log.error("Region Service: Region code already exists");
            throw new GeneralBusinessException("Region code already exists");
        }
        return true;
    }

    public boolean isUpdateValidation(RegionRequest regionRequest, Region region){
        if (regionRequest.getName() == null || regionRequest.getName().isEmpty()){
            log.error("Region Service: Region name is empty");
            throw new GeneralBusinessException("Region name is empty");
        }
        if (regionRequest.getCode() == null || regionRequest.getCode().isEmpty()){
            log.error("Region Service: Region code is empty");
            throw new GeneralBusinessException("Region code is empty");
        }

        if (regionRepository.existsByName(regionRequest.getName()) && !regionRequest.getName().equals(region.getName())){
            log.error("Region Service: Region name already exists");
            throw new GeneralBusinessException("Region name already exists");
        }

        if (regionRepository.existsByCode(regionRequest.getCode()) && !regionRequest.getCode().equals(region.getCode())){
            log.error("Region Service: Region code already exists");
            throw new GeneralBusinessException("Region code already exists");
        }
        return true;
    }

    private Region mapToEntity(RegionRequest regionRequest) {
        RegionDtoMapper regionDtoMapper = new RegionDtoMapper();
        return regionDtoMapper.convertToEntity(regionRequest,organizationService);
    }

    private RegionResponse mapToResponse(Region region) {
        RegionDtoMapper regionDtoMapper = new RegionDtoMapper();
        return regionDtoMapper.convertToResponse(region);
    }
}
