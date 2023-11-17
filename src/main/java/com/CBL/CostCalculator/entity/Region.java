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
        name = "Region"
)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "region_name", nullable = false)
    private String name;

    @Column(name = "region_code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;


}
