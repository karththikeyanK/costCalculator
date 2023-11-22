package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.Shop;
import com.CBL.CostCalculator.entity.TransportCost;
import com.CBL.CostCalculator.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransportCostRepository extends JpaRepository<TransportCost, Integer> {




}
