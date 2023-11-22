package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.TransportCostManger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportCostManagerRepository extends JpaRepository<TransportCostManger, Integer> {
}
