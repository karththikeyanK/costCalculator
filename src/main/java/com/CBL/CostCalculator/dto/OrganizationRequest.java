package com.CBL.CostCalculator.dto;

import com.CBL.CostCalculator.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequest {
    private String name;
    private String regNo;


    public Organization convertToEntity(){
        Organization organization = new Organization();
        organization.setName(this.name);
        organization.setRegNo(this.regNo);
        return organization;
    }
}
