package com.CBL.CostCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private Integer id;
    private String type;
    private String vehicleNumber;
    private Integer capacity;
    private Double costPerKM;
    private Integer regionId;
}
