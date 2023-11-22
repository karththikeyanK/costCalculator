package com.CBL.CostCalculator.dtoMapper;

import com.CBL.CostCalculator.dto.OrganizationRequest;
import com.CBL.CostCalculator.dto.OrganizationResponse;
import com.CBL.CostCalculator.entity.Organization;


public class OrganizationDtoMapper {

    public Organization convertToEntity(OrganizationRequest organizationRequest) {
        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setRegNo(organizationRequest.getRegNo());
        return organization;
    }

    public OrganizationResponse convertToResponse(Organization organization) {
        OrganizationResponse organizationResponse = new OrganizationResponse();
        organizationResponse.setId(organization.getId());
        organizationResponse.setName(organization.getName()); // Adjust based on response mapping requirement
        organizationResponse.setRegNo(organization.getRegNo()); // Adjust based on response mapping requirement
        return organizationResponse;
    }
}

