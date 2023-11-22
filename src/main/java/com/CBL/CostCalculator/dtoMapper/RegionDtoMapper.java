package com.CBL.CostCalculator.dtoMapper;

import com.CBL.CostCalculator.dto.RegionRequest;
import com.CBL.CostCalculator.dto.RegionResponse;
import com.CBL.CostCalculator.entity.Organization;
import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.service.OrganizationService;

public class RegionDtoMapper {



    public Region convertToEntity(RegionRequest regionRequest,OrganizationService organizationService) {
        Organization org = organizationService.getOrganizationEntity(regionRequest.getOrgId());
        Region region = new Region();
        region.setName(regionRequest.getName());
        region.setCode(regionRequest.getCode());
        region.setOrganization(org);
        return region;
    }

    public RegionResponse convertToResponse(Region region) {
        RegionResponse regionResponse = new RegionResponse();
        regionResponse.setId(region.getId());
        regionResponse.setName(region.getName());
        regionResponse.setCode(region.getCode());
        regionResponse.setOrgId(region.getOrganization().getId());
        return regionResponse;
    }

}
