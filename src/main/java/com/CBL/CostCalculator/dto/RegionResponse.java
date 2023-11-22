package com.CBL.CostCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {
    private Integer id;
    private String name;
    private String code;
    private Integer orgId;

}
