package com.CBL.CostCalculator.dto;

import com.CBL.CostCalculator.entity.Organization;
import com.CBL.CostCalculator.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequest {

    private String name;
    private String code;
    private Integer orgId;

}
