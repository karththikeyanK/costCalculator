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
        name = "Shops"
)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_name", nullable = false)
    private String name;

    @Column(name = "shop_reg_number", nullable = false)
    private String regNumber;

    @Column(name = "shop_address", nullable = false)
    private String address;

    @Column(name = "shop_contact", nullable = false)
    private String contact;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
