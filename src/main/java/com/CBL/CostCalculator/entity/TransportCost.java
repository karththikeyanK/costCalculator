package com.CBL.CostCalculator.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "TransportCost"
)
public class TransportCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "jobDate", nullable = false)
    private LocalDate Date;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TransportCost_Shop",
            joinColumns = @JoinColumn(name = "transportCost_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id")
    )
    @JsonManagedReference
    private Set<Shop> shops = new HashSet<>();


}
