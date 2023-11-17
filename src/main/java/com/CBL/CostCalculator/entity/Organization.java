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
        name = "Organization"
)
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "org_name", nullable = false)
    private String name;

    @Column(name = "org_regNo", nullable = false)
    private String regNo;


}
