package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.TransportCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportCostRepository extends JpaRepository<TransportCost, Integer> {
}