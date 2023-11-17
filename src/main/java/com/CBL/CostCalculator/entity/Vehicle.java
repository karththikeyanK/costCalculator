package com.CBL.CostCalculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "Vehicle"
)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vehicle_type", nullable = false)
    private String type;

    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "vehicle_capacity", nullable = false)
    private Integer capacity;

    @Column(name = "cost_per_KM", nullable = false)
    private Double costPerKM;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;


}
