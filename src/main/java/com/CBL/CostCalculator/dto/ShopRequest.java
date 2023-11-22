package com.CBL.CostCalculator.dto;

import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopRequest {
    private String name;
    private String regNumber;
    private String address;
    private String contact;
    private Double distance;
    private Integer regionId;



}
