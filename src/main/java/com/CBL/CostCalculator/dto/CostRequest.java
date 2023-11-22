package com.CBL.CostCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRequest {
    private Double cost;
    private String status;
    private LocalDate date;
    private Integer vehicleId;
    private List<Integer> shopId;
}
