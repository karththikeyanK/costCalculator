package com.CBL.CostCalculator.service;

import com.CBL.CostCalculator.dto.OrganizationRequest;
import com.CBL.CostCalculator.dto.OrganizationResponse;
import com.CBL.CostCalculator.dtoMapper.OrganizationDtoMapper;
import com.CBL.CostCalculator.entity.Organization;
import com.CBL.CostCalculator.exception.GeneralBusinessException;
import com.CBL.CostCalculator.repo.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {
        log.info("Organization Service: Creating organization started");
        try{
            if (isValidation(organizationRequest)){
                Organization org = organizationRepository.save(mapToEntity(organizationRequest));
                log.info("Organization Service: Organization object created");
                OrganizationResponse organizationResponse = mapToResponse(org);
                log.info("Organization Service: Creating organization completed");
                return organizationResponse;
            }
            throw new GeneralBusinessException("Organization creation failed");
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

            OrganizationResponse organizationResponse = mapToResponse(org);
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

    public List<OrganizationResponse> getAllOrganization() {
        log.info("Organization Service: Getting all organizations started");
        List<Organization> org = organizationRepository.findAll();

        if (org.isEmpty()) {
            log.error("Organization Service: No organizations found");
            throw new GeneralBusinessException("No organizations found");
        }

        List<OrganizationResponse> organizationResponseList = org.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        log.info("Organization Service: Getting all organizations completed");
        return organizationResponseList;
    }


    public OrganizationResponse updateOrganization(Integer id, OrganizationRequest organizationRequest){
        log.info("Organization Service: Updating organization started");
        try{
            Organization org = organizationRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Organization with id " + id + " does not exist"));
            log.info("Organization Service: Organization object retrieved");

           if (isUpdateValidation(organizationRequest,org)){
               org.setName(organizationRequest.getName());
               org.setRegNo(organizationRequest.getRegNo());

                organizationRepository.save(org);
                log.info("Organization Service: Organization object updated");
                OrganizationResponse organizationResponse = mapToResponse(org);
                log.info("Organization Service: Updating organization completed");
                return organizationResponse;
            }
           throw new GeneralBusinessException("Organization update failed");
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while updating organization: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while updating organization: "+ ex.getMessage());
            throw ex;
        }
    }

    public String deleteOrganization(Integer id){
        log.info("Organization Service: Deleting organization started");
        try{
            Organization org = organizationRepository.findById(id).orElseThrow(() -> new GeneralBusinessException("Organization with id " + id + " does not exist"));
            log.info("Organization Service: Organization object retrieved");
            organizationRepository.delete(org);
            log.info("Organization Service: Organization object deleted");
            return "Organization deleted successfully";
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while deleting organization: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while deleting organization: "+ ex.getMessage());
            throw ex;
        }
    }


    public boolean isValidation(OrganizationRequest organizationRequest){
        if (organizationRequest.getName().isEmpty() || organizationRequest.getRegNo().isEmpty() || organizationRequest.getName() == null || organizationRequest.getRegNo() == null){
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
        return true;
    }

    public boolean isUpdateValidation(OrganizationRequest organizationRequest, Organization organization){
        if (organizationRequest.getName().isEmpty() || organizationRequest.getRegNo().isEmpty() || organizationRequest.getName() == null || organizationRequest.getRegNo() == null){
            throw new GeneralBusinessException("Organization name and registration number cannot be empty");
        }

        if (organizationRepository.existsByName(organizationRequest.getName()) && !organizationRequest.getName().equals(organization.getName())){
            log.error("Organization Service: Organization name already exists");
            throw new GeneralBusinessException("Organization name already exists");
        }

        if (organizationRepository.existsByRegNo(organizationRequest.getRegNo()) && !organizationRequest.getRegNo().equals(organization.getRegNo())){
            log.error("Organization Service: Organization registration number already exists");
            throw new GeneralBusinessException("Organization registration number already exists");
        }
        return true;
    }

    public Organization getOrganizationEntity(Integer orgId) {
        try{
            log.info("Organization Service: Getting organization entity started");
            Organization organization = organizationRepository.findById(orgId).orElseThrow(() -> new GeneralBusinessException("Organization with id " + orgId + " does not exist"));
            log.info("Organization Service: Getting organization entity completed");
            return organization;
        }catch (GeneralBusinessException ex){
            log.error("Organization Service: Error while getting organization entity: "+ ex.getMessage());
            throw ex;
        }catch (Exception ex){
            log.error("Organization Service: Unexpected error while getting organization entity: "+ ex.getMessage());
            throw ex;
        }
    }

    private OrganizationResponse mapToResponse(Organization organization) {
        OrganizationDtoMapper mapper = new OrganizationDtoMapper();
        return mapper.convertToResponse(organization);
    }


    private Organization mapToEntity(OrganizationRequest organizationRequest) {
        OrganizationDtoMapper mapper = new OrganizationDtoMapper();
        return mapper.convertToEntity(organizationRequest);
    }
}
