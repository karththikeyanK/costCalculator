package com.CBL.CostCalculator.service;

import com.CBL.CostCalculator.dto.OrganizationRequest;
import com.CBL.CostCalculator.dto.OrganizationResponse;
import com.CBL.CostCalculator.entity.Organization;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {
        log.info("Organization Service: Creating organization started");
        try{
            if (organizationRequest.getName().isEmpty() || organizationRequest.getRegNo().isEmpty()){
                throw new GeneralBusinessException("Organization name and registration number cannot be empty");
            }

            if (organizationRepository.existsByName(organizationRequest.getName())){
                log.error("Organization Service: Organization name already exists");
                throw new GeneralBusinessException("Organization name already exists");
            }

            if (organizationRepository.existsByRegNo(organizationRequest.getRegNo())){
                log.error("Organization Service: Organization registration number already exists");
                throw new GeneralBusinessException("Organization registration number already exists");
            }
            Organization org = organizationRepository.save(organizationRequest.convertToEntity());
            log.info("Organization Service: Organization object created");
            OrganizationResponse organizationResponse = new OrganizationResponse().convertToResponse(org);
            log.info("Organization Service: Creating organization completed");
            return organizationResponse;
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while creating organization: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while creating organization: "+ ex.getMessage());
            throw ex;
        }
    }

    public OrganizationResponse getOrganization(Integer id){
        log.info("Organization Service: Getting organization started");
        try{
            Organization org = organizationRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Organization with id " + id + " does not exist"));
            log.info("Organization Service: Organization object retrieved");
            OrganizationResponse organizationResponse = new OrganizationResponse().convertToResponse(org);
            log.info("Organization Service: Getting organization completed");
            return organizationResponse;
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while getting organization: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while getting organization: "+ ex.getMessage());
            throw ex;
        }
    }

public OrganizationResponse updateOrganization(Integer id, OrganizationRequest organizationRequest){
        log.info("Organization Service: Updating organization started");
        try{
            Organization org = organizationRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Organization with id " + id + " does not exist"));
            log.info("Organization Service: Organization object retrieved");
            org.setName(organizationRequest.getName());
            org.setRegNo(organizationRequest.getRegNo());
            Organization updatedOrg = organizationRepository.save(org);
            log.info("Organization Service: Organization object updated");
            OrganizationResponse organizationResponse = new OrganizationResponse().convertToResponse(updatedOrg);
            log.info("Organization Service: Updating organization completed");
            return organizationResponse;
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while updating organization: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while updating organization: "+ ex.getMessage());
            throw ex;
        }
    }
}
