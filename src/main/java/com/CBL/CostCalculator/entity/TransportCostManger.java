package com.CBL.CostCalculator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
        name = "TransportCostManger"
)
public class TransportCostManger {
    @Id
    private Integer id;

    @ManyToOne
    private TransportCost transportCost;

    @ManyToOne
    private Shop shop;
}
