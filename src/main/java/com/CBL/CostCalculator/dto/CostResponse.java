package com.CBL.CostCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostResponse {
    private Integer id;
    private Double cost;
    private String status;
    private LocalDate Date;
    private Integer vehicleId;
    private List<Integer> shopId;
}
